package ru.nsu.fit.oop.arturploter.task_2_1_1;

import com.fasterxml.jackson.annotation.JsonProperty;

class PizzaChef implements Runnable {

  @JsonProperty("id")
  private final int id;

  @JsonProperty("cookingTime")
  private final int cookingTime;

  private boolean waitingForOrder;

  private Warehouse warehouse;
  private IncomingOrders incomingOrders;
  private PizzaRestaurantHeadquarters pizzaRestaurantHeadquarters;
  private PizzaChefs pizzaChefs;

  PizzaChef(@JsonProperty("id") int id, @JsonProperty("cookingTime") int cookingTime) {
    this.id = id;
    this.cookingTime = cookingTime;
    waitingForOrder = false;
  }

  boolean isWaitingForOrder() {
    return waitingForOrder;
  }

  void setWarehouse(Warehouse warehouse) {
    this.warehouse = warehouse;
  }

  void setIncomingOrders(IncomingOrders incomingOrders) {
    this.incomingOrders = incomingOrders;
  }

  void setPizzaRestaurantHeadquarters(PizzaRestaurantHeadquarters pizzaRestaurantHeadquarters) {
    this.pizzaRestaurantHeadquarters = pizzaRestaurantHeadquarters;
  }

  void setPizzaChefs(PizzaChefs pizzaChefs) {
    this.pizzaChefs = pizzaChefs;
  }

  @Override
  public void run() {

    while (!pizzaRestaurantHeadquarters.isRestaurantClosed()
        || !incomingOrders.areThereAnyOrders()) {

      Order currentOrder;

      try {
        waitingForOrder = true;
        pizzaChefs.lock.lock();

        if (pizzaRestaurantHeadquarters.isRestaurantClosed()
            && incomingOrders.areThereAnyOrders()) {
          break;
        }

        currentOrder = incomingOrders.takeOrder();
        currentOrder.setPizzaChefId(id);

        waitingForOrder = false;
      } finally {
        pizzaChefs.lock.unlock();
      }

      System.out.println(
          "PIZZA CHEF #" + id + " is making a pizza. ORDER #" + currentOrder.getId() + ".");

      System.out.println(
          "PIZZA CHEF #"
              + id
              + " just finished making a pizza. ORDER #"
              + currentOrder.getId()
              + ".");

      System.out.println("PIZZA CHEF #" + id + " stuck your pizza in the warehouse.");
      warehouse.putItemAwayInWarehouse(currentOrder);
    }

    while (true) {
      pizzaRestaurantHeadquarters.endShiftForPizzaChef();
      System.out.println("PIZZA CHEF #" + id + " is done for today.");
      break;
    }
  }

  int getId() {
    return id;
  }

  int getCookingTime() {
    return cookingTime;
  }
}
