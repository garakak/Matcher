import utils._
import Operations.{buy, orderCompletion, sell}

import scala.collection.mutable
import scala.collection.immutable.RedBlackTree

object Main extends App {

  val testListOfClients1 = List(Client("C1", 1000, 10, 5, 15, 0),
    Client("C2", 2000, 10, 5, 15, 0), Client("C3", 1000, 10, 5, 15, 0))
  val testListOfOrders1 = List(Order("C1", "b", "A", 5, 10),
    Order("C2", "s", "A", 5, 10), Order("C3", "s", "A", 5, 10), Order("C1", "b", "A", 5, 10))


  /**
    * Returns the list of clients after comparing
    *
    * WARNING: Algorithm is experimental and uses variables, so it should be tested.
    */
  def mainRespond(inputOrders: List[Order], inputClients: List[Client]) = (inputClients, inputOrders) match {

    case (_, List()) =>
      println(EmptyOrderResponse(message = "There are no active orders"))

    case (List(), _) =>
      println(EmptyClientsResponse(message = "There are no clients"))


    case _ =>
      var localOrders: mutable.Buffer[Order] = inputOrders.tail.toBuffer
      var firstOrder: Order = inputOrders.head

      var localClients: mutable.Buffer[Client] = inputClients.toBuffer

      for (secondOrder <- localOrders) {
        if (checkOrder(firstOrder, secondOrder)) {
          //println(firstOrder, secondOrder)

          val firstClient = inputClients.find(_.name == firstOrder.client).get
          val secondClient = inputClients.find(_.name == secondOrder.client).get
          val firstOrderStable = firstOrder
          println(firstOrderStable)

        {
          if (firstOrder.operation == "s" && secondOrder.operation == "b") {

            localClients = localClients.map { case `firstClient` => sell(firstClient, firstOrder); case x => x }
            localClients = localClients.map { case `secondClient` => buy(secondClient, secondOrder); case x => x }

            localOrders = localOrders.map { case `firstOrderStable` => orderCompletion(firstOrder); case x => x }
            localOrders = localOrders.map { case `secondOrder` => orderCompletion(secondOrder); case x => x }

            //localOrders = localOrders.filter((x: Order) => !x.isCompleted) //???
            firstOrder = localOrders.head

          } else {

            localClients = localClients.map { case `firstClient` => buy(firstClient, firstOrder); case x => x }
            localClients = localClients.map { case `secondClient` => sell(secondClient, secondOrder); case x => x }

            localOrders = localOrders.map { case `firstOrderStable` => orderCompletion(firstOrder); case x => x }
            localOrders = localOrders.map { case `secondOrder` => orderCompletion(secondOrder); case x => x }

            //localOrders = localOrders.filter((x: Order) => !x.isCompleted)
            firstOrder = localOrders.head

          }
        }
      }
      }
      localClients
    }


  /**
    * Checks, if order is correct
    */
  def checkOrder(firstRequest: Order, secondRequest: Order): Boolean = {

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
      order2 <- input_orders if checkOrder(order1, order2)

      firstClient = input_clients.find(_.name == order1.client).get //TODO: Can be modified with "getOrElse" to avoid
      secondClient = input_clients.find(_.name == order2.client).get //TODO: operations from non-existing clients.

      tmp <- {
        if (order1.operation == "s" && !order1.isCompleted && !order2.isCompleted) {

          input_orders.map { case `order1` => orderCompletion(order1); case x => x }
          input_orders.map { case `order1` => orderCompletion(order2); case x => x }
          input_clients.map { case `firstClient` => sell(firstClient, order1); case x => x }
          input_clients.map { case `secondClient` => buy(secondClient, order2); case x => x }

        } filter {
          old => old != firstClient && old != secondClient
        }
        else if (order1.operation == "b" && !order1.isCompleted && !order2.isCompleted) {

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

 println(mainRespond(testListOfOrders1, testListOfClients1))


}
