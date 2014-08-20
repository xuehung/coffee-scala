package controllers.auth

import play.api.mvc._
import play.api.mvc.{Action, RequestHeader,Result, Controller}
//import controllers.auth.UserEntity
import models.User


/*
 * ref: https://www.tunnelbear.com/development/authenticating-users-in-the-play-framework/
 */


trait Authentication {
  self:Controller =>
  var accessConditions: List[Conditions.Condition] = List.empty

  def AuthenticateMe(f: (Request[AnyContent], UserEntity) => Result) = Action { implicit request =>
    val user = AuthUtils.parseUserFromRequest

    if(user.isEmpty)
      Redirect("/login")
      //Forbidden("Invalid username or password")
    else {
      accessConditions.map(condition => condition(user.get)).collectFirst[String]{case Left(error) => error}
      match {
        //case Some(error) => Forbidden(s"Conditions not met: $error")
        case Some(error) => Redirect("/login")
        case _ => f(request, user.get)
      }
    }
  }
}

object Conditions {
  type Condition = (UserEntity => Either[String, Unit])
  def isPremiumUser:Condition = {
    user => if(user.isPremium)
      Right()
    else
      Left("User must be premium")
  }


  def balanceGreaterThan(required:Int):Condition = {
    user => if(user.balance > required)
      Right()
    else
      Left(s"User balance must be > $required")
  }
}

trait PremiumUsersOnly {
  self:Authentication =>
  accessConditions = accessConditions :+ Conditions.isPremiumUser
}

trait BalanceCheck {
  self:Authentication =>
  def getRequiredBalance:Int
  accessConditions = accessConditions :+ Conditions.balanceGreaterThan(getRequiredBalance)
}

object AuthUtils {
  //def parseUserFromCookie(implicit request: RequestHeader) = request.session.get("username").flatMap(username => User.find(username))
  def parseUserFromCookie(implicit request: RequestHeader) = {
    val email = request.cookies.get("email")
    val token = request.cookies.get("token")
    val ip = request.remoteAddress
    (email, token, ip) match {
      // @ is %40 in cookies
      case (Some(e), Some(t), i: String) => TokenPool.getEntity(e.value.replace("%40", "@"), t.value, i)
      case _ => None
    }
  }


  def parseUserFromQueryString(implicit request:RequestHeader) = {
    val query = request.queryString.map { case (k, v) => k -> v.mkString }
    //val username = query get ("username")
    //val password = query get ("password")
    val email = query get ("email")
    val token = query get ("token")
    val ip = request.remoteAddress
    (email, token, ip) match {
      //case (Some(u), Some(p)) => User.find(u).filter(user => user.checkPassword(p))
      case (Some(e), Some(t), i: String) => TokenPool.getEntity(e, t, i)
      case _ => None
    }
  }

  def parseUserFromRequest(implicit request:RequestHeader) = {
    parseUserFromQueryString orElse  parseUserFromCookie
  }

}
