package models

import play.api.Play.current
import scala.language.postfixOps
import org.joda.time._
import scalikejdbc._

case class Task(
  id: Long,
  label: String,
  ymd: Option[DateTime] = None
)

object Task {
  val task = (rs: WrappedResultSet) => Task(
    id = rs.long("id"), 
    label = rs.string("label"), 
    ymd = rs.timestampOpt("tmd").map(_.toDateTime)
  )

  def all()(implicit session: DBSession = AutoSession): List[Task] = {
    SQL("select * from task").map(task).list.apply()
  }

  def create(task: Task) {
    // DB.withConnection { implicit c =>
    //   SQL("insert into task (label) values ({label})").on(
    //     'label -> task.label
    //   ).executeUpdate()
    // }
  }

  def delete(id: Long) {
    // DB.withConnection { implicit c =>
    //   SQL("delete from task where id = {id}").on(
    //     'id -> id
    //   ).executeUpdate()
    // }
  }
}
