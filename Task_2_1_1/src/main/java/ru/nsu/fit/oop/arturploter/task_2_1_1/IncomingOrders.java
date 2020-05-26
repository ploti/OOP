package ru.nsu.fit.oop.arturploter.task_2_1_1;

import java.util.concurrent.LinkedBlockingQueue;

class IncomingOrders {
  private final LinkedBlockingQueue<Order> pendingOrders;

  IncomingOrders() {
    pendingOrders = new LinkedBlockingQueue<>();
  }

  void order(Order order) {
    try {
      pendingOrders.put(order);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  Order takeOrder() {
    try {
      return pendingOrders.take();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    return null;
  }

  boolean areThereAnyOrders() {
    return pendingOrders.isEmpty();
  }
}
