package controllers

// import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

import models.Tasks

import scala.slick.driver.H2Driver.simple._
import scala.slick.session.Session

object Application extends Controller {
  
  val taskForm = Form(
    "label" -> nonEmptyText
  )

  def index = Action {
    Redirect(routes.Application.tasks)
  }

	def tasks = Action {
		Ok(views.html.index(Tasks.all(), taskForm))
	}
  
  def newTask = Action { implicit request =>
    taskForm.bindFromRequest.fold(
      errors => BadRequest(views.html.index(Tasks.all, errors)),
      label => {
        Tasks.create(label)
        Redirect(routes.Application.tasks)
      }
    )
  }
  
  def deleteTask(id: Long) = Action {
    Tasks.delete(id)
    Redirect(routes.Application.tasks)
  }
  
}
