package controllers

import org.squeryl.Schema
import org.squeryl.PrimitiveTypeMode._
import org.squeryl.Table
import models._

object Database extends Schema {
  val usersTable: Table[User] = table[User]("tb_users")
  val subscribeTable: Table[Subscribe] = table[Subscribe]("tb_subscribe")
  val scheduleTable: Table[Schedule] = table[Schedule]("tb_schedule")
  val productTable: Table[Product] = table[Product]("tb_product")
  val deliverTable: Table[Product] = table[Product]("tb_deliver")

  on(usersTable) { u => declare {
    u.id is(autoIncremented)
  }}
  on(subscribeTable) { s => declare {
    s.id is(autoIncremented)
  }}
  on(scheduleTable) { s => declare {
    s.id is(autoIncremented)
  }}
  on(productTable) { p => declare {
    p.id is(autoIncremented)
  }}
  on(subscribeTable) { d => declare {
    d.id is(autoIncremented)
  }}
}
