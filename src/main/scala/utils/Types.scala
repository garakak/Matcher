package utils

/**
  * Basic client type
  */
case class Client(name: String,
                  dollarBalance: Int,
                  balanceA: Int,
                  balanceB: Int,
                  balanceC: Int,
                  balanceD: Int)

/**
  * Basic order type
  */
case class Order(client: String,
                 operation: String,
                 currency: String,
                 price: Int,
                 count: Int,
                 isCompleted: Boolean = false)
