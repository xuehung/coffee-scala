package models

import org.squeryl.PrimitiveTypeMode._
import org.squeryl.Table
import org.squeryl.Query
import collection.Iterable
import controllers.Database.{usersTable}

/*
package controllers.models

object User {
  val users = List(
    User("bob@tunnelbear.com", "password", true, 10),
    User("alice@tunnelbear.com", "password", true, 0),
    User("jane@tunnelbear.com", "password", false, 10)
  )
  def find(username: String):Option[User] = users.filter(_.email == username).headOption
  def findByEmail(email: String):Option[User] = users.filter(_.email == email).headOption
}

case class User(email:String, password:String, isPremium:Boolean, balance:Int) {
  def checkPassword(password: String): Boolean = this.password == password

}
*/
import org.squeryl.KeyedEntity

trait transactionInfo {
  var cardNum: String
  var cvc: String
  var expMon: String
  var expYear: String
}

case class User(
  id: Long,
  email: String,
  password: String,
  name: String,
  isPremium: Boolean,
  balance: Int
) extends KeyedEntity[Long] {
  def checkPassword(password: String): Boolean = this.password == password
}

object User {
  //def find(email: String):Option[User] = users.filter(_.email == email).headOption
  //def find(email: String): Option[User] = None
  def find(email: String) = {
    val q = from(usersTable) {user =>
      where(user.email === email).select(user)
    }
    inTransaction {
      val userList = q.toList
      if (userList.length ==0) None else Some(userList(0))
    }
  }

  def getAll = {
    val q = from(usersTable){user => select(user)}
    inTransaction { q.toList }
  }

  def create(user: User): User = inTransaction {
    usersTable.insert(user)
  }

}
