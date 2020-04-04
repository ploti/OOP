package ru.nsu.fit.oop.arturploter.task_2_1_1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class PizzaChefs {
  final Lock lock;
  private final List<FutureObjectPair> pizzaChefsWithTheirPizzas;

  PizzaChefs() {
    this.pizzaChefsWithTheirPizzas = new ArrayList<>();
    lock = new ReentrantLock(true);
  }

  void run(
      Employees employees,
      Warehouse warehouse,
      IncomingOrders incomingOrders,
      PizzaRestaurantHeadquarters pizzaRestaurantHeadquarters) {

    pizzaRestaurantHeadquarters.setNumOfPizzaChefs(employees.pizzaChefs.length);
    ExecutorService executorService = Executors.newFixedThreadPool(employees.pizzaChefs.length);

    for (PizzaChef pizzaChef : employees.pizzaChefs) {
      pizzaChef.setPizzaChefs(this);
      pizzaChef.setPizzaRestaurantHeadquarters(pizzaRestaurantHeadquarters);
      pizzaChef.setWarehouse(warehouse);
      pizzaChef.setIncomingOrders(incomingOrders);

      pizzaChefsWithTheirPizzas.add(
          new FutureObjectPair(pizzaChef, executorService.submit(pizzaChef)));
    }
  }

  List<FutureObjectPair> getPizzaChefsWithTheirPizzas() {
    return pizzaChefsWithTheirPizzas;
  }
}
