package ru.nsu.fit.oop.arturploter.task_2_1_1;

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
  private boolean restaurantIsClosed;

  /** Zeroes all the statistics and data. */
  public PizzaRestaurantHeadquarters() {
    currentOrderId = 0;
    numOfCompletedOrders = 0;
    numOfPizzaChefsFinishedWork = 0;
    numOfDeliveryWorkersFinishedWork = 0;
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
  public int getCurrentOrderId() {
    return currentOrderId;
  }

  void updateCurrentOrderId() {
    currentOrderId++;
  }

  void closeRestaurant() {
    restaurantIsClosed = true;
  }

  void completeOrder() {
    numOfCompletedOrders++;
  }

  void setNumOfPizzaChefs(int numOfPizzaChefs) {
    this.numOfPizzaChefs = numOfPizzaChefs;
  }

  void setNumOfDeliveryWorkers(int numOfDeliveryWorkers) {
    this.numOfDeliveryWorkers = numOfDeliveryWorkers;
  }

  void endShiftForPizzaChef() {
    numOfPizzaChefsFinishedWork++;
  }

  void endShiftForDeliveryWorker() {
    numOfDeliveryWorkersFinishedWork++;
  }
}
