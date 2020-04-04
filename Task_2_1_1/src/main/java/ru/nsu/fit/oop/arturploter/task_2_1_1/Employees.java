package ru.nsu.fit.oop.arturploter.task_2_1_1;

import com.fasterxml.jackson.annotation.JsonProperty;

class Employees {

  @JsonProperty("pizzaChefs")
  final PizzaChef[] pizzaChefs;

  @JsonProperty("deliveryWorkers")
  final DeliveryWorker[] deliveryWorkers;

  Employees(
      @JsonProperty("pizzaChefs") PizzaChef[] pizzaChefs,
      @JsonProperty("deliveryWorkers") DeliveryWorker[] deliveryWorkers) {

    this.pizzaChefs = pizzaChefs;
    this.deliveryWorkers = deliveryWorkers;
  }
}
