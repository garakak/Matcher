# DISCLAIMER: most of the logic in this prototype is old/wrong, or uses another algorithms.
# Was added to understand basic algorithmic logic and conditions of the task and to see, how it can be realised.

clients = ['C1 1000 10 5 15 0',
           'C2 2000 10 35 40 10']


orders = ['C1 b A 7 12',
          'C2 s A 7 12']


# Map, that refers to "currency" -> "Position in an order operation"
currencies = {"A": 2,
              "B": 3,
              "C": 4,
              "D": 5}


parsed_clients = []
for i in clients:
    j = i.split(" ")
    parsed_clients.append(j)


parsed_orders = []
for i in orders:
    j = i.split(" ")
    parsed_orders.append(j)


# Simple realisation of "sell" operation
def sell(client, currency, price, count):
    client[1] = int(client[1]) + int(count) * int(price)
    client[currencies.get(currency)] = int(client[currencies.get(currency)]) - int(count)
    return client


# Simple realisation of "buy" operation
def buy(client, currency, price, count):
    client[1] = int(client[1]) - int(count) * int(price)
    client[currencies.get(currency)] = int(client[currencies.get(currency)]) + int(count)
    return client


# Checks if two orders comply all the requirements
def check_order(first_client_request, second_client_request):
    if (first_client_request[0] != second_client_request[0]) and \
       (first_client_request[1] != second_client_request[1]) and \
       (first_client_request[2] == second_client_request[2]) and \
       (first_client_request[3] == second_client_request[3]) and \
       (first_client_request[4] == second_client_request[4]):

        first_client = find_client(first_client_request[0])
        second_client = find_client(second_client_request[0])

        if first_client_request[1] == 's':
            completed_client_1 = sell(first_client,
                                      first_client_request[2],
                                      first_client_request[3],
                                      first_client_request[4])

            completed_client_2 = buy(second_client,
                                     second_client_request[2],
                                     second_client_request[3],
                                     second_client_request[4])
        else:
            completed_client_2 = sell(second_client,
                                      second_client_request[2],
                                      second_client_request[3],
                                      second_client_request[4])

            completed_client_1 = buy(first_client,
                                     first_client_request[2],
                                     first_client_request[3],
                                     first_client_request[4])

        return completed_client_1, completed_client_2
    else:
        return ['None'], ['None']


# Finds required client in a list of total clients
def find_client(name):
    for k in parsed_clients:
            if name == k[0]:
                return k


# Works with O(n^2) speed, can be optimized if necessary, I've used the simplest algorithm to complete the task faster
def processing():
    fst_order = 0
    snd_order = 1
    while parsed_orders[fst_order] is not None:
        while parsed_orders[snd_order] is not None:
            new_client1, new_client2 = check_order(parsed_orders[fst_order], parsed_orders[snd_order])
            if new_client1[0] != 'None':
                find_client(new_client1)
                find_client(new_client2)
            snd_order += 1

        fst_order += 1


print(processing())
