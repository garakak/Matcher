import org.scalatest.Matchers
import org.scalatest.Matchers._
import Main._
import utils.{Client, Order}

class unitTests extends Matchers {

  final val emptyList = List()

  val testListOfClients1 = List(Client("C1", 1000, 10, 5, 15, 0),
                                Client("C2", 2000, 10, 5, 15, 0))
  val testListOfOrders1 = List(Order("C1", "b", "A", 5, 10),
                               Order("C2", "s", "A", 5, 10))
  val result1 = List(Client("C2", 2050, 0, 5, 15, 0), Client("C1", 950, 20, 5, 15, 0))

  affectedClients(testListOfOrders1, testListOfClients1) should be (result1)


  val testListOfClients2 = List(Client("C1", 1000, 10, 5, 15, 0),
                                Client("C2", 2000, 10, 5, 15, 0))
  val testListOfOrders2 = List(Order("C1", "b", "A", 5, 10),
                               Order("C1", "s", "A", 5, 10))

  affectedClients(testListOfOrders2, testListOfClients2) should be (emptyList)


  val testListOfClients3 = List(Client("C1", 1000, 10, 5, 15, 0),
                                Client("C2", 2000, 10, 5, 15, 0))
  val testListOfOrders3 = List(Order("C1", "s", "A", 5, 10),
                               Order("C2", "b", "A", 5, 10))
  val result3 = List(Client("C2", 1950, 20, 5, 15, 0), Client("C1", 1050, 0, 5, 15, 0))

  affectedClients(testListOfOrders3, testListOfClients3) should be (result3)


  val testListOfClients4 = List(Client("C1", 1000, 10, 5, 15, 0),
                                Client("C2", 2000, 10, 5, 15, 0))
  val testListOfOrders4 = List(Order("C1", "s", "A", 5, 10),
                               Order("C2", "b", "Z", 5, 10))

  affectedClients(testListOfOrders4, testListOfClients4) should be (emptyList)


  val testListOfClients5 = List(Client("C1", 1000, 10, 5, 15, 0),
                                Client("C2", 2000, 10, 5, 15, 0),
                                Client("C3", 2000, 10, 5, 15, 0))
  val testListOfOrders5 = List(Order("C1", "b", "A", 5, 10),
                               Order("C2", "s", "A", 5, 10))
  val result5 = List(Client("C2", 2050, 0, 5, 15, 0), Client("C3", 2000, 10, 5, 15, 0), Client("C1", 950, 20, 5, 15, 0))

  affectedClients(testListOfOrders5, testListOfClients5) should be (result5)


  val testListOfClients6 = List(Client("C1", 1000, 10, 5, 15, 0),
                                Client("C2", 2000, 10, 5, 15, 0),
                                Client("C3", 2000, 10, 5, 15, 0))
  val testListOfOrders6 = List(Order("C1", "b", "A", 5, 10),
                               Order("C2", "s", "A", 5, 10),
                               Order("C3", "s", "B", 5, 10))
  val result6: List[Client] = result5

  affectedClients(testListOfOrders6, testListOfClients6) should be (result6)




  // THIS TEST CAN BE USED ONLY AFTER RESOLVING GETTING "None" VALUE WHILE HAVING ORDER FROM NON_EXISTING CLIENT
//  val testListOfClients7 = List(Client("C1", 1000, 10, 5, 15, 0),
//                                Client("C2", 2000, 10, 5, 15, 0))
//  val testListOfOrders7 = List(Order("C1", 'b', 'A', 5, 10),
//                               Order("C3", 's', 'A', 5, 10))
//
//  main(testListOfOrders7, testListOfClients4) should be (emptyList)


}
