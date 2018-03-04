package utils

import scala.io.Source

object Readers {

  /**
    * Reads the file of clients and returns list
    */
  def readClients(fileName: String): List[Client] = {

    Source.fromFile(fileName).getLines().toList map { line =>
      line.trim.split("\\s+")
      match {
        case Array(name, dollars, balA, balB, balC, balD) =>
          Client(
            name,
            dollars.toInt,
            balA.toInt,
            balB.toInt,
            balC.toInt,
            balD.toInt)
      }
    }
  }

  /**
    * Reads the file of orders and returns list
    */
  def readOrders(fileName: String): List[Order] = {
    Source.fromFile(fileName).getLines().toList map { line =>
      line.trim.split("\\s+")
      match {
        case Array(client, operation, curr, price, count) =>
          Order(
            client,
            operation.head,
            curr.head,
            price.toInt,
            count.toInt)
      }
    }
  }

}
