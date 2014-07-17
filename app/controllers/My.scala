package controllers

import play.api.mvc._
import controllers.auth.Authentication


//object My extends Controller with Authentication with PremiumUsersOnly with BalanceCheck {
object My extends Controller with Authentication {

  //override def getRequiredBalance = 8

  def myConsole = AuthenticateMe { (request, user) =>
    Ok(s"hello ${user.email}")
  }

}


