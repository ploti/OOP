package ru.nsu.fit.oop.arturploter.task_2_1_1;

import java.util.HashMap;
import java.util.Map;

/**
 * The class represents the statistics of the restaurant and other important data.
 *
 * @author Artur Ploter
 */
public class PizzaRestaurantHeadquarters {
  private int currentOrderId;
  private int numOfCompletedOrders;
  private int numOfPizzaChefs;
  private int numOfDeliveryWorkers;
  private int numOfPizzaChefsFinishedWork;
  private int numOfDeliveryWorkersFinishedWork;
  private int numOfDaysPassedSinceTheBeginningOfTheWeek;
  private double earningsThisWeek;
  private double revenue;
  private Map<Integer, Integer> deliveryWorkersJobPerformance;
  private Map<Integer, Integer> pizzaChefsJobPerformance;
  private boolean restaurantIsClosed;

  /** Zeroes all the statistics and data. */
  public PizzaRestaurantHeadquarters() {
    currentOrderId = 0;
    numOfCompletedOrders = 0;
    numOfPizzaChefsFinishedWork = 0;
    numOfDeliveryWorkersFinishedWork = 0;
    numOfDaysPassedSinceTheBeginningOfTheWeek = 0;
    earningsThisWeek = 0;
    revenue = 0;
    deliveryWorkersJobPerformance = new HashMap<>();
    pizzaChefsJobPerformance = new HashMap<>();
    restaurantIsClosed = false;
  }

  /**
   * Returns {@code true} if the restaurant is closed.
   *
   * @return {@code true} if the restaurant is closed
   */
  public boolean isRestaurantClosed() {
    return restaurantIsClosed;
  }

  /**
   * Returns {@code true} if all orders are completed.
   *
   * @return {@code true} if all orders are completed
   */
  public boolean areAllOrdersCompleted() {
    return currentOrderId == numOfCompletedOrders;
  }

  /**
   * Returns {@code true} if all pizza chefs finished their work.
   *
   * @return {@code true} if all pizza chefs finished their work
   */
  public boolean areAllPizzaChefsFinishedWork() {
    return numOfPizzaChefs == numOfPizzaChefsFinishedWork;
  }

  /**
   * Returns {@code true} if all delivery workers finished their work.
   *
   * @return {@code true} if all delivery workers finished their work
   */
  public boolean areAllDeliveryWorkersFinishedWork() {
    return numOfDeliveryWorkers == numOfDeliveryWorkersFinishedWork;
  }

  /**
   * Returns current order ID.
   *
   * @return current order ID
   */
  public int getAvailableOrderId() {
    return currentOrderId;
  }

  void updateCurrentOrderId() {
    currentOrderId++;
  }

  void closeRestaurant() {
    restaurantIsClosed = true;
  }

  void openRestaurant() {
    restaurantIsClosed = false;
  }

  void completeOrder(Order order) {
    order.completeOrder();
    numOfCompletedOrders++;

    double total = order.getTotal();
    revenue -= (total / 2);

    if (order.getOrderProcessingTime() > order.getTimeLimit()) {
      setEmployeeJobPerformance(pizzaChefsJobPerformance, order.getPizzaChefId());
      setEmployeeJobPerformance(deliveryWorkersJobPerformance, order.getDeliveryWorkerId());

      System.out.println(
          "It took more than "
              + order.getTimeLimit()
              + " seconds to deliver the pizza. We had to give it away for free.");
    } else {
      System.out.printf("Money earned: $%.2f.\n", total);
      earningsThisWeek += total;
      revenue += total;
    }
  }

  void addOneDayToNumOfDaysPassedSinceTheBeginningOfTheWeek() {
    numOfDaysPassedSinceTheBeginningOfTheWeek++;
    numOfPizzaChefsFinishedWork = 0;
    numOfDeliveryWorkersFinishedWork = 0;
  }

  void endShiftForPizzaChef() {
    numOfPizzaChefsFinishedWork++;
  }

  void endShiftForDeliveryWorker() {
    numOfDeliveryWorkersFinishedWork++;
  }

  void startNextWeek() {
    currentOrderId = 0;
    numOfCompletedOrders = 0;
    numOfPizzaChefsFinishedWork = 0;
    numOfDeliveryWorkersFinishedWork = 0;
    numOfDaysPassedSinceTheBeginningOfTheWeek = 0;
    earningsThisWeek = 0;
    revenue = 0;
    deliveryWorkersJobPerformance = new HashMap<>();
    pizzaChefsJobPerformance = new HashMap<>();
    restaurantIsClosed = false;
  }

  int getNumOfDaysPassedSinceTheBeginningOfTheWeek() {
    return numOfDaysPassedSinceTheBeginningOfTheWeek;
  }

  int getNumOfCompletedOrders() {
    return numOfCompletedOrders;
  }

  int getNumOfPizzaChefs() {
    return numOfPizzaChefs;
  }

  void setNumOfPizzaChefs(int numOfPizzaChefs) {
    this.numOfPizzaChefs = numOfPizzaChefs;
  }

  int getNumOfDeliveryWorkers() {
    return numOfDeliveryWorkers;
  }

  void setNumOfDeliveryWorkers(int numOfDeliveryWorkers) {
    this.numOfDeliveryWorkers = numOfDeliveryWorkers;
  }

  Map<Integer, Integer> getDeliveryWorkersJobPerformance() {
    return deliveryWorkersJobPerformance;
  }

  Map<Integer, Integer> getPizzaChefsJobPerformance() {
    return pizzaChefsJobPerformance;
  }

  double getEarningsThisWeek() {
    return earningsThisWeek;
  }

  double getRevenue() {
    return revenue;
  }

  private void setEmployeeJobPerformance(
      Map<Integer, Integer> employeeJobPerformance, int workerId) {
    if (!employeeJobPerformance.containsKey(workerId)) {
      employeeJobPerformance.put(workerId, -2);
    } else {
      employeeJobPerformance.put(workerId, employeeJobPerformance.get(workerId) - 2);
    }
  }
}
