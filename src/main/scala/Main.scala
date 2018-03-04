import scala.util.{Failure, Success, Try}
import scala.concurrent.ExecutionContext.Implicits.global
import java.io._
import java.nio.file.Files
import java.nio.file.Paths

import Operations.{sell, buy, orderCompletion}

object Main extends App {

  //val source = scala.io.Source.fromFile("file.txt")
  //val lines = try source.mkString.split("/t") finally source.close()

  case class Client(name: String,
                    dollarBalance: Int,
                    balanceA: Int,
                    balanceB: Int,
                    balanceC: Int,
                    balanceD: Int)

  case class Order(client: String,
                   operation: Char,
                   currency: Char,
                   price: Int,
                   count: Int,
                   isCompleted: Boolean = false)

  val listOfClients = List(Client("C1", 1000, 10, 5, 15, 0),
                           Client("C2", 2000, 10, 5, 15, 0),
    Client("C3", 2000, 10, 5, 15, 0)
                           )

  val listOfOrders: List[Order] = List(Order("C1", 'b', 'A', 5, 10),
                                       Order("C2", 's', 'A', 5, 10),
    Order("C3", 's', 'A', 5, 10)
    )

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


  def affectedClients(input_orders: List[Order], input_clients: List[Client]): List[Client] = {
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

  if (affectedClients(listOfOrders, listOfClients) == List()) println(listOfClients)
  else println(affectedClients(listOfOrders, listOfClients))

//  val outcome_result: List[Client] = main(listOfOrders, listOfClients)
//
//  val content = outcome_result.mkString("\n").getBytes
//  Files.write(Paths.get("result.txt"), content)
}
