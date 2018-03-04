import org.scalatest.Matchers
import org.scalatest.Matchers._
import Main._

class unitTests extends Matchers {

  val testListOfClients1 = List(Client("C1", 1000, 10, 5, 15, 0),
                                Client("C2", 2000, 10, 5, 15, 0))
  val testListOfOrders1 = List(Order("C1", 'b', 'A', 5, 10),
                               Order("C2", 's', 'A', 5, 10))
  val result1 = List(Client("C2",2050,0,5,15,0), Client("C1",950,20,5,15,0))

  main(testListOfOrders1, testListOfClients1) should be (result1)


  val testListOfClients2 = List(Client("C1", 1000, 10, 5, 15, 0),
                                Client("C2", 2000, 10, 5, 15, 0))
  val testListOfOrders2 = List(Order("C1", 'b', 'A', 5, 10),
                               Order("C1", 's', 'A', 5, 10))
  val result2 = List()

  main(testListOfOrders2, testListOfClients2) should be (result2)


  val testListOfClients3 = List(Client("C1", 1000, 10, 5, 15, 0),
                                Client("C2", 2000, 10, 5, 15, 0))
  val testListOfOrders3 = List(Order("C1", 's', 'A', 5, 10),
                               Order("C2", 'b', 'A', 5, 10))
  val result3 = List(Client("C2",1950,20,5,15,0), Client("C1",1050,0,5,15,0))

  main(testListOfOrders3, testListOfClients3) should be (result3)


//  val testListOfClients4 = List(Client("C1", 1000, 10, 5, 15, 0),
//                                Client("C2", 2000, 10, 5, 15, 0),
//                                Client("C3", 3000, 10, 5, 15, 0))
//  val testListOfOrders4 = List(Order("C1", 'b', 'A', 5, 10),
//                               Order("C2", 's', 'A', 5, 10))
//  val result4 = List(Client("C2",2050,0,5,15,0), Client("C1",950,20,5,15,0))
//
//  main(testListOfOrders4, testListOfClients4) should be (result4)
}
