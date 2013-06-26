package models

import anorm._
import anorm.SqlParser._
import play.api.db._
import play.api.Play.current
import scala.language.postfixOps

case class Task(id: Long, label: String, ymd: Option[org.joda.time.DateTime])

object Task {
  
  val task = {
    get[Long]("id") ~ 
    get[String]("label") ~
    get[org.joda.time.DateTime]("ymd") map {
      case id~label~ymd => Task(id, label, ymd)
    }
  }

  def all(): List[Task] = DB.withConnection { implicit c =>
    SQL("select * from task").as(task *)
  }
  
  def create(task: Task) {
    DB.withConnection { implicit c =>
      SQL("insert into task (label) values ({label})").on(
        'label -> task.label
      ).executeUpdate()
    }
  }

  def delete(id: Long) {
    DB.withConnection { implicit c =>
      SQL("delete from task where id = {id}").on(
        'id -> id
      ).executeUpdate()
    }
  }
}

