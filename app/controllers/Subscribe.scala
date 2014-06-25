package controllers

import play.api.mvc._
import play.api.data.Form
import play.api.data.Forms.{mapping, longNumber, nonEmptyText}
import play.api.i18n.Messages

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;


import models.User


object Subscribe extends Controller {

  def newSubscribe = Action { request =>
    val body: AnyContent = request.body
    val formMapOption: Option[Map[String, Seq[String]]] = body.asFormUrlEncoded

    try {
      if(formMapOption.isEmpty) {
        BadRequest("A form is expected")
      }
      val formMap = formMapOption.get
      val email = formMap("email")(0)
      //val address = formMap("address")(0)

      /*
      Stripe.apiKey = "sk_test_BQokikJOvBiI2HlWgH4olfQ2";
      var chargeParams = new java.util.HashMap[String, Object]()
      //Map<String, Object> chargeParams = new HashMap<String, Object>();
      chargeParams.put("amount", 400: java.lang.Integer);
      //chargeParams.put("amount", 400);
      chargeParams.put("currency", "usd");
      var cardParams = new java.util.HashMap[String, Object]()
      //Map<String, Object> cardParams = new HashMap<String, Object>();
      cardParams.put("number", "4242424242424242");
      cardParams.put("exp_month", 6: java.lang.Integer);
      cardParams.put("exp_year", 2015: java.lang.Integer);
      //cardParams.put("exp_month", 6);
      //cardParams.put("exp_year", 2015);
      cardParams.put("cvc", "314");
      chargeParams.put("card", cardParams);
      chargeParams.put("description", "Charge for test@example.com");

      Charge.create(chargeParams);
      */
      val user = User.find(email)
      if(user.isEmpty) {
        val newUser = User(0, email, "password", "Mike", true, 0)
        User.create(newUser)
        Ok("ok")
      } else {
        BadRequest(email + " already exists")
      }

    } catch {
      case e: Exception => BadRequest(e.toString)
    }


  }

  def list = Action { request =>
    //import Database.{usersTable}
    //def allQ: Query[User] = from(usersTable) {
    //  user => select(user)}
    val users = User.getAll
    Ok(users.length.toString)
  }


}


