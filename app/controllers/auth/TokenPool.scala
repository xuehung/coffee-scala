package controllers.auth

import models.User

object TokenPool {
  var pool: Map[String, UserEntity] = Map()

  def add(user: User, ip: String): String = {
    val token = java.util.UUID.randomUUID.toString
    val userEntity = UserEntity(user.email, user.isPremium, user.balance, token, (new java.util.Date()).getTime, ip)
    pool += (user.email -> userEntity)
    return token
  }

  def checkToken(email: String, token: String, ip: String) = {
    pool.contains(email) && pool(email).ip == ip && pool(email).token == token
  }

  def getEntity(email: String, token: String, ip: String): Option[UserEntity] = {
    if(checkToken(email, token, ip)) Some(pool(email)) else None
  }
}

//case class TokenPool()


