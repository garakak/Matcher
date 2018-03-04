# Matcher
Simplified realisation of stock exchange in Scala + Python prototype of it.

The stock has just 4 currencies: A, B, C, D and $ balance.

# Input data:
  1) File "clients.txt".
     Contains names of clients, their balance in $ and in every currency.
     
     Example format:    C1  1000    10  5   15  0 
     
         Name: C2, $ balance: 2000, A balance: 3, b balance: 35, C balance: 40, D balance: 10
                      
  2) File "orders.txt"                     
     Contains list of orders.
     
     Example format:    C1  b   A   7   12
     
         Client: C2, op: s, cur: A, p: 8, N: 10

         cur - currency, that client wants to buy,
         op - operation, "sell" or "buy"
         p - price of a currency
         N - count of a currency.


# Notes:

1) Partial matching is not included.
2) Self-orders are not included.
3) Clients balance may be negative.

# Output data:
  File "outcome.txt".
  Contains list of clients after realisation of all orders.

