package controllers.admin

import play.api.mvc._
import play.api.data.Form
import play.api.data.Forms.{mapping, longNumber, nonEmptyText}
import play.api.libs.json._
import play.api.i18n.Messages
import controllers.auth.Authentication
import controllers._
import controllers.auth._

import org.squeryl.PrimitiveTypeMode._
import org.squeryl.Table
import org.squeryl.Query
import collection.Iterable

import models.Product
import models.Schedule
import controllers.Database.{scheduleTable, productTable}

import java.text.SimpleDateFormat


object AdminApi extends Controller with Authentication with PremiumUsersOnly{

  def addProduct = AuthenticateMe { (request, user) =>
    val apiParams = new ApiParams(request)
    val r = new CoffeeResponse()

    try {
      val nameCh = apiParams.get("name_ch")
      val nameEng = apiParams.get("name_eng")
      val altitude = apiParams.get("altitude")
      val origin = apiParams.get("origin")

      val product = Product(0, nameCh, nameEng, altitude, origin)
      Product.create(product)

      Ok(r.success)

    } catch {
      case e: Exception => BadRequest(e.toString)
    }
  }

  //def addSchedule = AuthenticateMe { (request, user) =>
  def addSchedule = Action { request =>
    val apiParams = new ApiParams(request)
    val r = new CoffeeResponse()

    try {
      val dateStr = apiParams.get("date")
      val productId = apiParams.get("product_id")

      val formatter: SimpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
      val deliverDate = formatter.parse(dateStr);

      val schedule = new Schedule(0, productId.toInt, deliverDate, false)
      Schedule.create(schedule)

      Ok(r.success)

    } catch {
      case e: Exception => BadRequest(e.toString)
    }
  }

  //def showProduct = AuthenticateMe { (request, user) =>
  def showProduct = Action {
    val r = new CoffeeResponse()

    try {

      val products = Product.getAll
      var productsJson = new JsArray()
      for(p <- products) {
        val item = Json.obj (
          "id" -> p.id,
          "name" -> p.name_ch
        )
        productsJson :+= item
      }
      r.put("products", productsJson)

      Ok(r.success)

    } catch {
      case e: Exception => BadRequest(e.toString)
    }
  }



}


