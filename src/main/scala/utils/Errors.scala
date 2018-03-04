package utils

/**
  * Base class for definitions of specific errors.
  */
sealed abstract class Errors {
  val code: String
  val message: String
  val cur: Char

  val `NoSuchCurrency` = "NoSuchCurrency"

}

final case class NoSuchCurrencyError(message: String, cur: Char='?') extends Errors {
  val code: String = `NoSuchCurrency`
}
