package utils

/**
  * Base class for definitions of specific errors.
  */
sealed abstract class Errors {
  val code: String
  val message: String
  val resource: String

  val `NoSuchCurrency` = "NoSuchCurrency"
  val `NoSuchClient` = "NoSuchClient"

}

final case class NoSuchCurrencyError(message: String, resource: String="?") extends Errors {
  val code: String = `NoSuchCurrency`
}
