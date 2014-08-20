package models

import org.squeryl.PrimitiveTypeMode._
import org.squeryl.Table
import org.squeryl.Query
import collection.Iterable
import controllers.Database.{productTable}
import org.squeryl.KeyedEntity

case class Product(
  id: Long,
  name_ch: String,
  name_eng: String,
  altitude: String,
  origin: String
) extends KeyedEntity[Long]

object Product {
  def find(id: Int) = {
    val q = from(productTable) {product =>
      where(product.id === id).select(product)
    }
    inTransaction {
      val productList = q.toList
      if (productList.length ==0) None else Some(productList(0))
    }
  }

  def getAll = {
    val q = from(productTable){product => select(product)}
    inTransaction { q.toList }
  }

  def create(product: Product): Product = inTransaction {
    productTable.insert(product)
  }

}
