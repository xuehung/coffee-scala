package controllers

import play.api.libs.json._

class CoffeeResponse {
  var response = Json.obj()

  def put(key: String, value: String) = response ++= Json.obj(key -> value)
  def success() = {
    response ++= Json.obj("code" -> 200)
    response.toString()
  }
}
