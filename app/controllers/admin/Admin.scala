package controllers.admin

import play.api.mvc._
import play.api.data.Form
import play.api.data.Forms.{mapping, longNumber, nonEmptyText}
import play.api.i18n.Messages

import models.User


object Admin extends Controller {

  def list = Action { request =>
    val users = User.getAll
    Ok(views.html.userlist(users))
  }


}


