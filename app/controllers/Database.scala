package controllers

import org.squeryl.Schema
import org.squeryl.PrimitiveTypeMode._
import org.squeryl.Table
import models.User

object Database extends Schema {
  val usersTable: Table[User] = table[User]("tb_users")
  on(usersTable) { u => declare {
    u.id is(autoIncremented)
  }}
}
