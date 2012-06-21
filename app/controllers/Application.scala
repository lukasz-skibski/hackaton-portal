package controllers

import play.api._
import play.api.mvc._
import play.api.i18n._
import model.Model
import org.squeryl.PrimitiveTypeMode._
import org.squeryl.Schema
import org.squeryl.KeyedEntity
import org.squeryl.annotations.Column

object Application extends Controller with securesocial.core.SecureSocial {
  
  def index = UserAwareAction { implicit request =>
	Ok(views.html.index())
  }
  
  def about = Action {
    Ok(views.html.about())
  }
  
  def contact = Action {
    Ok(views.html.contact())
  }
  
  def sponsors = Action {
    Ok(views.html.sponsors())
  }

}