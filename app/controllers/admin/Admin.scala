package controllers.admin

import play.api.mvc._
import play.api.data.Form
import play.api.data.Forms.{mapping, longNumber, nonEmptyText}
import play.api.i18n.Messages

import controllers.auth._
import controllers.Database.{scheduleTable, productTable}

import models.Country
import models.User
import models.Product
import models.Schedule

import org.squeryl.PrimitiveTypeMode._
import org.squeryl.Table
import org.squeryl.Query
import collection.Iterable

//object Admin extends Controller with Authentication with PremiumUsersOnly {
object Admin extends Controller {

  //def userList = AuthenticateMe { (request, user) =>
  def userList = Action {
    val users = User.getAll
    Ok(views.html.admin.userlist(users))
  }

  //def countryList = AuthenticateMe { (request, user) =>
  def countryList = Action {
    val countries = Country.getCountries
    Ok(views.html.admin.countrylist(countries))
  }

  //def productList = Action { (request, user) =>
  def productList = Action {
    val products = Product.getAll
    Ok(views.html.admin.productlist(products))
  }

  //def scheduleList = AuthenticateMe { (request, user) =>
  def scheduleList = Action {
    try {
      val q = from(scheduleTable, productTable) {(s, p) =>
        where(s.product_id === p.id).select(s, p).orderBy(s.deliver_date)
      }
      inTransaction {
        val list = q.toList
        Ok(views.html.admin.schedulelist(list))
      }
    }
  }

}


