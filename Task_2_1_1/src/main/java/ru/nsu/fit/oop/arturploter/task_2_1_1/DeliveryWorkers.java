package ru.nsu.fit.oop.arturploter.task_2_1_1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class DeliveryWorkers {
  final Lock lock;
  private final List<Future<?>> orders;
  private ExecutorService executor;

  DeliveryWorkers() {
    orders = new ArrayList<>();
    lock = new ReentrantLock(true);
  }

  void run(
      Employees employees,
      Warehouse warehouse,
      PizzaRestaurantHeadquarters pizzaRestaurantHeadquarters) {

    pizzaRestaurantHeadquarters.setNumOfDeliveryWorkers(employees.deliveryWorkers.length);
    executor = Executors.newFixedThreadPool(employees.deliveryWorkers.length);

    for (DeliveryWorker deliveryWorker : employees.deliveryWorkers) {
      deliveryWorker.setDeliveryWorkers(this);
      deliveryWorker.setPizzaRestaurantHeadquarters(pizzaRestaurantHeadquarters);
      deliveryWorker.setWarehouse(warehouse);

      Future<?> future = executor.submit(deliveryWorker);
      orders.add(future);
    }
  }

  public ExecutorService getExecutor() {
    return executor;
  }

  List<Future<?>> getOrders() {
    return orders;
  }
}
