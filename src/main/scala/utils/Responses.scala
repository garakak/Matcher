package utils

/**
  * Base class for definitions of specific errors.
  */
sealed abstract class Responses {
  val code: String
  val message: String
  val resource: String

  val `EmptyOrders` = "EmptyOrders"
  val `EmptyClients` = "EmptyClients"
  val `NoSuchClient` = "NoSuchClient"

}

final case class EmptyOrderResponse(message: String, resource: String=" <main algorithm>") extends Responses {
  val code: String = `EmptyOrders`
}

final case class EmptyClientsResponse(message: String, resource: String="") extends Responses {
  val code: String = `EmptyClients`
}
