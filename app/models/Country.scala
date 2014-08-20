package models

import org.squeryl.PrimitiveTypeMode._
import org.squeryl.Table
import org.squeryl.Query
import collection.Iterable
import org.squeryl.KeyedEntity

case class Country(
  countryCode: String,
  shipment: Int
)

object Country {
  val countries = List(
    Country("TW", 200),
    Country("JP", 200),
    Country("CN", 200)
  )

  def getCountries(): List[Country] = countries
}
