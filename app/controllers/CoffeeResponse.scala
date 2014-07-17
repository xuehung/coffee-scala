package controllers

import play.api.libs.json._

class CoffeeResponse {
  var response = Json.obj()

  def put(key: String, value: String): CoffeeResponse = {
    response ++= Json.obj(key -> value)
    return this
  }

  def success() = {
    response ++= Json.obj("code" -> 200)
    response.toString()
  }

  def fail(errorMsg: String = "Unknow reason", errorCode: Int = 1) = {
    response ++= Json.obj("code" -> errorCode)
    response ++= Json.obj("message" -> errorMsg)
    response.toString()
  }
}
