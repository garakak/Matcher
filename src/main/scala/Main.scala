import scala.util.{Failure, Success, Try}
import scala.concurrent.ExecutionContext.Implicits.global
import java.io._
import java.nio.file.Files
import java.nio.file.Paths

import utils.Readers
import Operations.{buy, orderCompletion, sell}
import utils.{Client, Order}

import scala.io.Source

object Main extends App {

  val listOfClients: List[Client] = Readers.readClients("clients.txt")
  val listOfOrders: List[Order] = Readers.readOrders("orders.txt")

  val listOfClientsTest = List(Client("C1", 1000, 10, 5, 15, 0),
                           Client("C2", 2000, 10, 5, 15, 0),
    Client("C3", 2000, 10, 5, 15, 0))

  val listOfOrdersTest: List[Order] = List(Order("C1", 'b', 'A', 5, 10),
                                       Order("C2", 's', 'A', 5, 10),
    Order("C3", 's', 'A', 5, 10))

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


  def affectedClients(input_orders: List[utils.Order], input_clients: List[utils.Client]): List[utils.Client] = {
    for {
      order1 <- input_orders
      order2 <- input_orders if check_order(order1, order2)

      firstClient = input_clients.find(_.name == order1.client).get  //TODO: Can be modified with "getOrElse" to avoid
      secondClient = input_clients.find(_.name == order2.client).get //TODO: operations from non-existing clients.

      tmp <- {
        if (order1.operation == 's' && !order1.isCompleted && !order2.isCompleted) {

          input_orders.map { case `order1` => orderCompletion(order1); case x => x }
          input_orders.map { case `order1` => orderCompletion(order2); case x => x }
          input_clients.map { case `firstClient` => sell(firstClient, order1); case x => x }
          input_clients.map { case `secondClient` => buy(secondClient, order2); case x => x }

      }
//        filter {
//        old => old.dollarBalance != firstClient.dollarBalance && old.dollarBalance != secondClient.dollarBalance
//
//      }
        else if (order1.operation == 'b' && !order1.isCompleted && !order2.isCompleted) {

          input_orders.map { case `order1` => orderCompletion(order1); case x => x }
          input_orders.map { case `order1` => orderCompletion(order2); case x => x }
          input_clients.map { case `firstClient` => buy(firstClient, order1); case x => x }
          input_clients.map { case `secondClient` => sell(secondClient, order2); case x => x }

      }
//        filter {
//        old => old.dollarBalance != firstClient.dollarBalance && old.dollarBalance != secondClient.dollarBalance
//
//      }
        else {
          input_orders.map {x => x}
          input_clients.map {x => x}
        }
//        filter {
//          old => old.dollarBalance != firstClient.dollarBalance && old.dollarBalance != secondClient.dollarBalance
//        }
      }

    } yield tmp
  }.distinct

  if (affectedClients(listOfOrdersTest, listOfClientsTest) == List()) println(listOfClientsTest)
  else println(affectedClients(listOfOrdersTest, listOfClientsTest))

//  val outcome_result: List[Client] = main(listOfOrders, listOfClients)
//
//  val content = outcome_result.mkString("\n").getBytes
//  Files.write(Paths.get("result.txt"), content)
}
