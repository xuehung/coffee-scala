package models

import org.squeryl.PrimitiveTypeMode._
import org.squeryl.Table
import org.squeryl.Query
import collection.Iterable
import controllers.Database.{scheduleTable}
import org.squeryl.KeyedEntity
import java.util.{Date, Locale}

case class Schedule(
  id: Long,
  product_id: Int,
  deliver_date: Date,
  done: Boolean
) extends KeyedEntity[Long]

object Schedule {

  def getAll = {
    val q = from(scheduleTable){schedule => select(schedule)}
    inTransaction { q.toList }
  }

  def create(schedule: Schedule): Schedule = inTransaction {
    scheduleTable.insert(schedule)
  }

}
