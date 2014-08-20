package controllers.subscribe

import play.api.mvc._
import play.api.data.Form
import play.api.data.Forms.{mapping, longNumber, nonEmptyText}
import play.api.i18n.Messages
import controllers.auth.Authentication
import controllers._

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;


import models.User


object SubscribeApi extends Controller with Authentication {

  def newSubscribe = AuthenticateMe { (request, user) =>
    val apiParams = new ApiParams(request)
    val r = new CoffeeResponse()

    try {
      val country = apiParams.get("country")
      val address = apiParams.get("address")
      val size = apiParams.get("size")
      // credit card
      val cardNum = apiParams.get("card_num")
      val cardExpYear = apiParams.get("card_exp_year")
      val cardExpMonth = apiParams.get("card_exp_month")
      val cardCvc = apiParams.get("card_cvc")

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
      Ok(r.success)
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


