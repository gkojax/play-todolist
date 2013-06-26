package controllers

import jp.t2v.lab.play20.auth._
import play.api.data._
import play.api.data.Forms._
import play.api.templates._
import models._
import views._
import play.api.mvc._
import play.api.mvc.Results._
import reflect.{ClassTag, classTag}

object Application extends Controller with LoginLogout with AuthConfigImpl {

  val loginForm = Form {
    mapping("email" -> email, "password" -> text)(Account.authenticate)(_.map(u => (u.email, "")))
      .verifying("Invalid email or password", result => result.isDefined)
  }

  def login = Action { implicit request =>
    Ok(html.login(loginForm))
  }

  def logout = Action { implicit request =>
    gotoLogoutSucceeded.flashing(
      "success" -> "You've been logged out"
    )
  }

  def authenticate = Action { implicit request =>
    loginForm.bindFromRequest.fold(
      formWithErrors => BadRequest(html.login(formWithErrors)),
      user => gotoLoginSucceeded(user.get.id)
    )
  }
}

object Tasks extends Controller with Auth with AuthConfigImpl {

  val taskForm = Form(
    "label" -> nonEmptyText
  )

  def tasks = authorizedAction(NormalUser) { user => request =>
    Ok(views.html.tasks(Task.all(), taskForm))
  }

  def newTask = authorizedAction(Administrator) { user => implicit request =>
    taskForm.bindFromRequest.fold(
      errors => BadRequest(views.html.tasks(Task.all(), errors)),
      label => {
        Task.create(label)
        Redirect(routes.Tasks.tasks)
      }
    )
  }

  def deleteTask(id: Long) = authorizedAction(Administrator) { user => request =>
    Task.delete(id)
    Redirect(routes.Tasks.tasks)
  }
}

trait AuthConfigImpl extends AuthConfig {

  type Id = String

  type User = Account

  type Authority = Permission

  val idTag = classTag[Id]

  val sessionTimeoutInSeconds = 3600

  def resolveUser(id: Id) = Account.findById(id)

  def loginSucceeded(request: RequestHeader) = Redirect(routes.Tasks.tasks)

  def logoutSucceeded(request: RequestHeader) = Redirect(routes.Application.login)

  def authenticationFailed(request: RequestHeader) = Redirect(routes.Application.login)

  def authorizationFailed(request: RequestHeader) = Forbidden("no permission")

  def authorize(user: User, authority: Authority) = (user.permission, authority) match {
    case (Administrator, _) => true
    case (NormalUser, NormalUser) => true
    case _ => false
  }

  // override lazy val cookieSecureOption: Boolean = play.api.Play.current.configuration.getBoolean("auth.cookie.secure").getOrElse(true)


}
