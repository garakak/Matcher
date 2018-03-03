import java.util.NoSuchElementException
import java.util.concurrent.TimeUnit

import scala.concurrent.{Await, Future}
import scala.util.{Failure, Success, Try}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

object Main extends App {

  val source = scala.io.Source.fromFile("file.txt")
  val lines: String = try source.mkString finally source.close()

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
                   count: Int)

  val listOfClients = List(Client("C1", 1000, 10, 5, 15, 0),
                           Client("C2", 2000, 15, 4, 12, 1))

  val listOfOrders: List[Order] = List(Order("C1", 'b', 'A', 7, 12),
                          Order("C2", 's', 'A', 7, 12))

  /**
    * Function, that realises "sell" operation
    *
    * Realised via "case 'currency' => _" because there are just 4 currencies,
    * if there were more of them, other method would be chosen
    */
  def sell(client: Client, order: Order): Client = order.currency match {

    case 'A' =>
      Client(
        client.name,
        client.dollarBalance + order.price * order.count,
        client.balanceA - order.count,
        client.balanceB,
        client.balanceC,
        client.balanceD
      )

    case 'B' =>
      Client(
        client.name,
        client.dollarBalance + order.price * order.count,
        client.balanceA,
        client.balanceB - order.count,
        client.balanceC,
        client.balanceD
      )

    case 'C' =>
      Client(
        client.name,
        client.dollarBalance + order.price * order.count,
        client.balanceA,
        client.balanceB,
        client.balanceC - order.count,
        client.balanceD
      )

    case 'D' =>
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
    * if there were more of them, other method would be chosen
    */
  def buy(client: Client, order: Order): Client = order.currency match {

    case 'A' =>
      Client(
        client.name,
        client.dollarBalance - order.price * order.count,
        client.balanceA + order.count,
        client.balanceB,
        client.balanceC,
        client.balanceD
      )

    case 'B' =>
      Client(
        client.name,
        client.dollarBalance - order.price * order.count,
        client.balanceA,
        client.balanceB + order.count,
        client.balanceC,
        client.balanceD
      )

    case 'C' =>
      Client(
        client.name,
        client.dollarBalance - order.price * order.count,
        client.balanceA,
        client.balanceB,
        client.balanceC + order.count,
        client.balanceD
      )

    case 'D' =>
      Client(
        client.name,
        client.dollarBalance - order.price * order.count,
        client.balanceA,
        client.balanceB,
        client.balanceC,
        client.balanceD + order.count
      )
  }


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
          secondRequest.price)
  }


    val req1 = Order("C1", 'b', 'A', 7, 12)
    val req2 = Order("C1", 's', 'A', 7, 12)
    val cli1 = Client("C1", 1000, 10, 5, 15, 0)
    val cli2 = Client("C2", 2000, 15, 4, 12, 1)


  def res(input_orders: List[Order], input_clients: List[Client]): List[Client] = {
    for {
      order1 <- input_orders
      order2 <- input_orders if check_order(order1, order2)
      firstClient = input_clients.find(_.name == order1.client)
      secondClient = input_clients.find(_.name == order2.client)
      tmp <- if (order1.operation == 's') {

        input_clients.map { case `firstClient` => sell(firstClient.get, order1); case x => x }
        input_clients.map { case `secondClient` => buy(secondClient.get, order2); case x => x }
      } else {

        input_clients.map { case `firstClient` => buy(firstClient.get, order1); case x => x }
        input_clients.map { case `secondClient` => sell(secondClient.get, order2); case x => x }
      }
    } yield tmp
  }

  //println(sell(cli1, req1))
  println(res(listOfOrders, listOfClients))

}
