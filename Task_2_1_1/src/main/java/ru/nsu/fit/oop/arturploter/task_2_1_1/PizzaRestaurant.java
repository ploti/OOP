package ru.nsu.fit.oop.arturploter.task_2_1_1;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * The class represents a pizza restaurant with pizza chefs and delivery workers. Pizza chefs make
 * pizza and delivery workers deliver the pizza right to your doorstep! The class offers method to
 * start the restaurant.
 *
 * @author Artur Ploter
 */
public class PizzaRestaurant {
  private static final int WAITING_TIME_MILLISECONDS = 3000;

  private static final List<String> PIZZA_SIZES =
      new ArrayList<>(Arrays.asList("small", "medium", "large", "extra-large"));

  private static final List<String> PIZZAS =
      new ArrayList<>(
          Arrays.asList(
              "Margherita",
              "Marinara",
              "Quattro",
              "Carbonara",
              "Pizza al Pesto",
              "Bufalina",
              "Americana",
              "Calzone",
              "Mare e Monti",
              "Bufalina",
              "Mimosa",
              "Funghi",
              "Sicilian Pizza"));

  private final Employees employees;
  private final PizzaRestaurantHeadquarters pizzaRestaurantHeadquarters;
  private final IncomingOrders incomingOrders;
  private final Warehouse warehouse;
  private final PizzaChefs pizzaChefs;
  private final DeliveryWorkers deliveryWorkers;

  /**
   * Initializes the restaurant with the given employees parameters and warehouse capacity.
   *
   * @param employeesParameters parameters of pizza chefs and delivery workers
   * @param warehouseCapacity capacity of the warehouse
   */
  public PizzaRestaurant(File employeesParameters, int warehouseCapacity) {
    JSONReader reader = new JSONReader();
    employees = reader.readParameters(employeesParameters);
    pizzaRestaurantHeadquarters = new PizzaRestaurantHeadquarters();
    warehouse = new Warehouse(warehouseCapacity);
    incomingOrders = new IncomingOrders();
    pizzaChefs = new PizzaChefs();
    deliveryWorkers = new DeliveryWorkers();
  }

  /**
   * Places orders, processes them, closes the restaurant, and returns the {@code
   * pizzaRestaurantHeadquarters} object.
   *
   * @param numOfOrders total number of orders
   * @return the {@code pizzaRestaurantHeadquarters} object
   */
  public PizzaRestaurantHeadquarters start(int numOfOrders) {
    pizzaChefs.run(employees, warehouse, incomingOrders, pizzaRestaurantHeadquarters);
    deliveryWorkers.run(employees, warehouse, pizzaRestaurantHeadquarters);

    IntStream.range(0, numOfOrders).forEach(i -> order());
    closeRestaurant();

    return pizzaRestaurantHeadquarters;
  }

  private void order() {
    Random random = new Random();
    System.out.println(
        "ORDER #"
            + pizzaRestaurantHeadquarters.getCurrentOrderId()
            + ": You successfully ordered "
            + PIZZA_SIZES.get(random.nextInt(PIZZA_SIZES.size()))
            + " "
            + PIZZAS.get(random.nextInt(PIZZAS.size()))
            + ".");

    Order order = new Order(pizzaRestaurantHeadquarters.getCurrentOrderId());
    pizzaRestaurantHeadquarters.updateCurrentOrderId();
    incomingOrders.order(order);
  }

  private void closeRestaurant() {
    pizzaRestaurantHeadquarters.closeRestaurant();

    while (!incomingOrders.areThereNoOrders()) {
      try {
        Thread.sleep(WAITING_TIME_MILLISECONDS);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    for (FutureObjectPair getPizzaChefsWithTheirPizzas :
        pizzaChefs.getPizzaChefsWithTheirPizzas()) {

      PizzaChef pizzaChef = (PizzaChef) getPizzaChefsWithTheirPizzas.object;

      if (pizzaChef.isWaitingForOrder()) {
        getPizzaChefsWithTheirPizzas.future.cancel(true);
      }
    }

    while (!pizzaRestaurantHeadquarters.areAllPizzaChefsFinishedWork()
        || !pizzaRestaurantHeadquarters.areAllDeliveryWorkersFinishedWork()) {

      try {
        Thread.sleep(WAITING_TIME_MILLISECONDS);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
