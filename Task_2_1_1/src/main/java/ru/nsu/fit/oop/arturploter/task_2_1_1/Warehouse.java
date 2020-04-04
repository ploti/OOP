package ru.nsu.fit.oop.arturploter.task_2_1_1;

import java.util.concurrent.TimeUnit;

class Warehouse {
  private static MyArrayBlockingQueue<Order> itemsInWarehouse;

  Warehouse(int capacity) {
    itemsInWarehouse = new MyArrayBlockingQueue<>(capacity, true);
  }

  void putItemAwayInWarehouse(Order order) {
    try {
      itemsInWarehouse.put(order);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  Order pickItemForDelivery(int milliseconds) throws InterruptedException {
    return itemsInWarehouse.poll(milliseconds, TimeUnit.MILLISECONDS);
  }

  int numOfItemsInWarehouse() {
    return itemsInWarehouse.size();
  }
}
