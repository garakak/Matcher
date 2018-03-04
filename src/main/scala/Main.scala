import utils.Writer
import utils.Readers
import Operations.{buy, orderCompletion, sell}
import utils.{Client, Order}

object Main extends App {

  val listOfClients: List[Client] = Readers.readClients("clients.txt")
  val listOfOrders: List[Order] = Readers.readOrders("orders.txt")

  /**
    * Checks, if order is correct
    */
  def check_order(firstRequest: Order, secondRequest: Order): Boolean = {

    firstRequest.client != secondRequest.client &&
      firstRequest.operation != secondRequest.operation &&
      (firstRequest.currency,
        firstRequest.count,
        firstRequest.price) ==
        (secondRequest.currency,
          secondRequest.count,
          secondRequest.price) &&
      !firstRequest.isCompleted &&
      !secondRequest.isCompleted

  }

  /**
    * Returns the list of all the transactions
    */
  def affectedClients(input_orders: List[utils.Order], input_clients: List[utils.Client]): List[utils.Client] = {
    for {
      order1 <- input_orders
      order2 <- input_orders if check_order(order1, order2)

      firstClient = input_clients.find(_.name == order1.client).get //TODO: Can be modified with "getOrElse" to avoid
      secondClient = input_clients.find(_.name == order2.client).get //TODO: operations from non-existing clients.

      tmp <- {
        if (order1.operation == 's' && !order1.isCompleted && !order2.isCompleted) {

          input_orders.map { case `order1` => orderCompletion(order1); case x => x }
          input_orders.map { case `order1` => orderCompletion(order2); case x => x }
          input_clients.map { case `firstClient` => sell(firstClient, order1); case x => x }
          input_clients.map { case `secondClient` => buy(secondClient, order2); case x => x }

        } filter {
          old => old != firstClient && old != secondClient
        }
        else if (order1.operation == 'b' && !order1.isCompleted && !order2.isCompleted) {

          input_orders.map { case `order1` => orderCompletion(order1); case x => x }
          input_orders.map { case `order1` => orderCompletion(order2); case x => x }
          input_clients.map { case `firstClient` => buy(firstClient, order1); case x => x }
          input_clients.map { case `secondClient` => sell(secondClient, order2); case x => x }

        } filter {
          old => old != firstClient && old != secondClient
        }
        else {
          input_orders.map { x => x }
          input_clients.map { x => x }
        } filter {
          old => old != firstClient && old != secondClient
        }
      }

    } yield tmp
  }.distinct

  // Writing to a file is combined with printing for better visualisation
  if (affectedClients(listOfOrders, listOfClients).isEmpty) {
    Writer.writeToFile(listOfClients)
    println(listOfClients)
  } else {
    Writer.writeToFile(affectedClients(listOfOrders, listOfClients))
    println(affectedClients(listOfOrders, listOfClients))
  }
}
