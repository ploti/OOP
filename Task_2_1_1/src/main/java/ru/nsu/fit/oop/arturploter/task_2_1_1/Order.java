package ru.nsu.fit.oop.arturploter.task_2_1_1;

import java.time.Duration;
import java.time.Instant;

class Order {

  private final int id;
  private final int timeLimit;
  private final double total;
  private final Instant pizzaOrderedTime;
  private int pizzaChefId;
  private int deliveryWorkerId;
  private Instant pizzaDeliveredTime;

  Order(int id) {
    this.id = id;
    timeLimit = 30;
    total = 10.99;
    pizzaOrderedTime = Instant.now();
  }

  Order(int id, int timeLimit, double total) {
    this.id = id;
    this.timeLimit = timeLimit;
    this.total = total;
    pizzaOrderedTime = Instant.now();
  }

  void completeOrder() {
    pizzaDeliveredTime = Instant.now();
  }

  long getOrderProcessingTime() {
    if (pizzaDeliveredTime == null) {
      throw new IllegalStateException("You should complete the order first.");
    }

    return Duration.between(pizzaOrderedTime, pizzaDeliveredTime).toMillis() / 1000;
  }

  int getId() {
    return id;
  }

  public double getTotal() {
    return total;
  }

  public int getPizzaChefId() {
    return pizzaChefId;
  }

  public void setPizzaChefId(int pizzaChefId) {
    this.pizzaChefId = pizzaChefId;
  }

  public int getDeliveryWorkerId() {
    return deliveryWorkerId;
  }

  public void setDeliveryWorkerId(int deliveryWorkerId) {
    this.deliveryWorkerId = deliveryWorkerId;
  }

  int getTimeLimit() {
    return timeLimit;
  }
}
