package controllers

import java.util.Date
import org.squeryl.PrimitiveTypeMode._
import org.squeryl.Schema
import play.api.mvc._
import play.api._
import model.Hackathon
import play.api.data._
import play.api.data.Forms._
import scala.collection.mutable.ListBuffer

object News extends Controller {

  val newsForm = Form(
    mapping(
      "title" -> nonEmptyText,
      "text" -> nonEmptyText,
      "author_id" -> play.api.data.Forms.longNumber,
      "published" -> date("dd/MM/yyyy"))(model.News.apply)(model.News.unapply))

  def index = Action { implicit request =>
    var newsList: List[model.News] = null

    transaction {
      newsList = Hackathon.news.seq.toList
    }

    Ok(views.html.news.index(newsList))
  }

  def createForm = Action { implicit request =>
    var users: List[model.User] = null

    transaction {
      users = Hackathon.users.seq.toList
    }

    Ok(views.html.news.create(newsForm, users))
  }

  def create = Action {
    implicit request =>
      newsForm.bindFromRequest.fold(
        errors => {
          var users: List[model.User] = null

          transaction {
            users = Hackathon.users.seq.toList
          }
          
          BadRequest(views.html.news.create(errors, users))
        },
        news => {
          transaction {
            Hackathon.news.insert(news)
          }
          Redirect(routes.News.index).flashing("status" -> "News added")
        })
  }

  def delete(id: Long) = Action {

    transaction {
      Hackathon.news.deleteWhere(n => n.id === id)
    }

    Redirect(routes.News.index).flashing("status" -> "News deleted")
  }

}