package controllers.auth

import play.api.mvc._
import play.api.data.Form
import play.api.data.Forms.{mapping, longNumber, nonEmptyText}
import controllers.CoffeeResponse
import controllers.ApiParams
import models.User

object AuthApi extends Controller {

  def login = Action { request =>
    val apiParams = new ApiParams(request)
    val r = new CoffeeResponse()

    try {
      val email = apiParams.get("email")
      val password = apiParams.get("password")

      val user = User.find(email)
      val ip = request.remoteAddress

      r.put("email", email)
      if(user.isEmpty || !user.get.checkPassword(password)) {
        Ok(r.fail("Login failed"))
      } else {
        val token = TokenPool.add(user.get, ip)
        r.put("token", token)
        Ok(r.success())
      }
    } catch {
      case e: Exception => BadRequest(e.toString)
    }
  }

  def register = Action { request =>
    val apiParams = new ApiParams(request)
    val r = new CoffeeResponse()

    try {
      val email = apiParams.get("email")
      val password = apiParams.get("password")
      val name = apiParams.get("name")

      val user = User.find(email)
      val ip = request.remoteAddress

      if(!user.isEmpty) {
        Ok(r.fail("This email already exists"))
      } else {
        // create a user object
        val newUser = User(0, email, password, name, false, 0)
        // insert the new user into database
        User.create(newUser)
        // login with this email
        val token = TokenPool.add(newUser, ip)
        r.put("token", token)
        r.put("email", email)
        Ok(r.success())
      }
    } catch {
      case e: Exception => BadRequest(e.toString)
    }
  }

}

