package controllers

import play.api.mvc._
import play.api.mvc.Request
import play.api.data.Form
import play.api.data.Forms.{mapping, longNumber, nonEmptyText}

class ApiParams(request: Request[AnyContent]) {
  val body: AnyContent = request.body
  val formMapOption: Option[Map[String, Seq[String]]] = body.asFormUrlEncoded
  val formMap = formMapOption.get

  def get(param: String): String = {
    formMap(param)(0)
  }
}
