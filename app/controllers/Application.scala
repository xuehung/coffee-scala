package controllers

import play.api.mvc._


object Application extends Controller {

  def index = Action {
    Ok(views.html.index("hello world"))
  }

  def aboutus = Action {
    Ok(views.html.index("aboutus"))
  }

  def login = Action {
    Ok(views.html.login())
  }

}


