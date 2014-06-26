package controllers

import play.api.mvc._


object My extends Controller with Authentication with PremiumUsersOnly with BalanceCheck {

  override def getRequiredBalance = 8

  def helloUser = AuthenticateMe {
    user => Ok(s"hello ${user.email}")
  }

}


