package models

import org.squeryl.PrimitiveTypeMode._
import org.squeryl.Table
import org.squeryl.Query
import collection.Iterable
import controllers.Database.{subscribeTable}
import org.squeryl.KeyedEntity

/*
object SubscribeStatus extends Enumeration {
  type SubscribeStatus = Value
  val NEW, VALID, INVALID = Value
}

import SubscribeStatus._
*/

case class Subscribe(
  id: Long,
  email: String,
  valid: Boolean,
  // payment info
  cardNum: String,
  cvc: String,
  expMon: String,
  expYear: String,
  size: Int,
  sucess: Boolean,
  // address info
  receiver: String,
  country: String,
  address: String,
  zip: String,
  // billing info
  billReceiver: String,
  invoiceNumber: String,
  billAddress: String,
  billZip: String
) extends KeyedEntity[Long]

object Subscribe {
  def create(subscribe: Subscribe): Subscribe = inTransaction {
    subscribeTable.insert(subscribe)
  }

  def find(email: String) = {
    val q = from(subscribeTable) {subscribe =>
      where(subscribe.email === email).select(subscribe)
    }
    inTransaction {
      val result = q.toList
      if (result.length ==0) None else Some(result(0))
    }
  }

  def getValidSubscribe = {
    val q = from(subscribeTable) {subscribe =>
      where(subscribe.valid === true).
      select(subscribe)
    }
    inTransaction { q.toList }
  }
}

