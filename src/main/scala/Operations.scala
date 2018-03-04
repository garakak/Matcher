import Main.{Client, Order}

object Operations {

  /**
    * Function, that realises "sell" operation
    *
    * Realised via "case 'currency' => _" because there are just 4 currencies,
    * if there were more of them, another method would be chosen
    */
  def sell(client: Client, order: Order): Client = order.currency match {

    case 'A' =>
      Order(order.client,
        order.operation,
        order.currency,
        order.price,
        order.count,
        isCompleted = true)

      Client(
        client.name,
        client.dollarBalance + order.price * order.count,
        client.balanceA - order.count,
        client.balanceB,
        client.balanceC,
        client.balanceD
      )

    case 'B' =>
      Order(order.client,
        order.operation,
        order.currency,
        order.price,
        order.count,
        isCompleted = true)

      Client(
        client.name,
        client.dollarBalance + order.price * order.count,
        client.balanceA,
        client.balanceB - order.count,
        client.balanceC,
        client.balanceD
      )

    case 'C' =>
      Order(order.client,
        order.operation,
        order.currency,
        order.price,
        order.count,
        isCompleted = true)

      Client(
        client.name,
        client.dollarBalance + order.price * order.count,
        client.balanceA,
        client.balanceB,
        client.balanceC - order.count,
        client.balanceD
      )


    case 'D' =>
      Order(order.client,
        order.operation,
        order.currency,
        order.price,
        order.count,
        isCompleted = true)

      Client(
        client.name,
        client.dollarBalance + order.price * order.count,
        client.balanceA,
        client.balanceB,
        client.balanceC,
        client.balanceD - order.count
      )
  }

  /**
    * Function, that realises "buy" operation
    *
    * Realised via "case 'currency' => _" because there are just 4 currencies,
    * if there were more of them, another method would be chosen
    */
  def buy(client: Client, order: Order): Client = order.currency match {

    case 'A' =>
      Order(order.client,
        order.operation,
        order.currency,
        order.price,
        order.count,
        isCompleted = true)

      Client(
        client.name,
        client.dollarBalance - order.price * order.count,
        client.balanceA + order.count,
        client.balanceB,
        client.balanceC,
        client.balanceD
      )

    case 'B' =>
      Order(order.client,
        order.operation,
        order.currency,
        order.price,
        order.count,
        isCompleted = true)

      Client(
        client.name,
        client.dollarBalance - order.price * order.count,
        client.balanceA,
        client.balanceB + order.count,
        client.balanceC,
        client.balanceD
      )

    case 'C' =>
      Order(order.client,
        order.operation,
        order.currency,
        order.price,
        order.count,
        isCompleted = true)

      Client(
        client.name,
        client.dollarBalance - order.price * order.count,
        client.balanceA,
        client.balanceB,
        client.balanceC + order.count,
        client.balanceD
      )

    case 'D' =>
      Order(order.client,
        order.operation,
        order.currency,
        order.price,
        order.count,
        isCompleted = true)

      Client(
        client.name,
        client.dollarBalance - order.price * order.count,
        client.balanceA,
        client.balanceB,
        client.balanceC,
        client.balanceD + order.count
      )
  }

}
