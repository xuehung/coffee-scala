package controllers.auth

import play.api.mvc._
import play.api.data.Form
import play.api.data.Forms.{mapping, longNumber, nonEmptyText}
import controllers.CoffeeResponse
import models.User

object Login extends Controller {

  def login = Action { request =>
    val body: AnyContent = request.body
    val formMapOption: Option[Map[String, Seq[String]]] = body.asFormUrlEncoded

    try {
      if(formMapOption.isEmpty) {
        BadRequest("A form is expected")
      }
      val formMap = formMapOption.get
      val email = formMap("email")(0)
      val password = formMap("password")(0)
      val user = User.find(email)
      val ip = request.remoteAddress

      if(user.isEmpty || !user.get.checkPassword(password)) {
        BadRequest("Login failed")
      } else {
        val token = TokenPool.add(user.get, ip)
        val r = new CoffeeResponse()
        r.put("token", token)
        r.put("email", email)
        Ok(r.success())
      }
    } catch {
      case e: Exception => BadRequest(e.toString)
    }

  }

}

