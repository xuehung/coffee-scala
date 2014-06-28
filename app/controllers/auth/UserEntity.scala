package controllers.auth

case class UserEntity(
  email: String,
  isPremium: Boolean,
  balance: Int,
  token: String,
  loginTime: Long,
  ip: String
)

