package controllers

import play.api.mvc._


object Application extends Controller {

  def index = Action {
    //Ok("hello world")
    Ok(views.html.index("hello world"))
  }

}


