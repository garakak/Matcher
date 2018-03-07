import utils.{Client, NoSuchCurrencyError, Order}

object Operations {

  final val mapOfCurrencies: Map[String, String] = Map(
    "A" -> "balanceA",
    "B" -> "balanceB",
    "C" -> "balanceC",
    "D" -> "balanceD"
  )

  /**
    * Function, that realises "sell" operation
    *
    * Realised via "case 'currency' => _" because there are just 4 currencies,
    * if there were more of them, another method would be chosen
    */
  def sell(client: Client, order: Order): Client = {

      if (mapOfCurrencies.contains(order.currency)) order.currency match {

        case "A" =>
          Client(
            client.name,
            client.dollarBalance + order.price * order.count,
            client.balanceA - order.count,
            client.balanceB,
            client.balanceC,
            client.balanceD
          )


        case "B" =>
          Client(
            client.name,
            client.dollarBalance + order.price * order.count,
            client.balanceA,
            client.balanceB - order.count,
            client.balanceC,
            client.balanceD
          )

        case "C" =>
          Client(
            client.name,
            client.dollarBalance + order.price * order.count,
            client.balanceA,
            client.balanceB,
            client.balanceC - order.count,
            client.balanceD
          )


        case "D" =>
          Client(
            client.name,
            client.dollarBalance + order.price * order.count,
            client.balanceA,
            client.balanceB,
            client.balanceC,
            client.balanceD - order.count
          )

      } else {
        println(NoSuchCurrencyError(s"Currency ${order.currency} not found"))
        client
      }
  }

  /**
    * Function, that realises "buy" operation
    *
    * Realised via "case 'currency' => _" because there are just 4 currencies,
    * if there were more of them, another method would be chosen
    */
  def buy(client: Client, order: Order): Client = {

    if (mapOfCurrencies.contains(order.currency)) order.currency match {

    case "A" =>
      Client(
        client.name,
        client.dollarBalance - order.price * order.count,
        client.balanceA + order.count,
        client.balanceB,
        client.balanceC,
        client.balanceD
      )

    case "B" =>
      Client(
        client.name,
        client.dollarBalance - order.price * order.count,
        client.balanceA,
        client.balanceB + order.count,
        client.balanceC,
        client.balanceD
      )

    case "C" =>
      Client(
        client.name,
        client.dollarBalance - order.price * order.count,
        client.balanceA,
        client.balanceB,
        client.balanceC + order.count,
        client.balanceD
      )

    case "D" =>
      Client(
        client.name,
        client.dollarBalance - order.price * order.count,
        client.balanceA,
        client.balanceB,
        client.balanceC,
        client.balanceD + order.count
      )

    } else {
      println(NoSuchCurrencyError(s"Currency ${order.currency} not found"))
      client
    }
  }

  /**
    * Function, that realises completion of an order
    *
    * count -> 0
    * price -> 0
    * isCompleted -> true
    */
  def orderCompletion(order: Order): Order = {
    Order(order.client,
      order.operation,
      order.currency,
      0,
      0,
      isCompleted = true)
  }

}
