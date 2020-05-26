package ru.nsu.fit.oop.arturploter.task_2_1_1;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
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
  private static final int DELIVERY_TIME_SECONDS_MIN = 4;
  private static final int DELIVERY_TIME_MINUTES_MAX = 20;

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

  private static final List<Double> PIZZA_PRICES =
      new ArrayList<>(Arrays.asList(10.59, 7.09, 11.0, 13.49, 4.99, 12.0, 21.09, 3.33, 5.99));

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
   * pizzaRestaurantHeadquarters} object. New free pizza delivery policy!
   *
   * @param numOfOrdersADay total number of orders
   * @return the {@code pizzaRestaurantHeadquarters} object
   */
  public PizzaRestaurantHeadquarters runPizzaRestaurantForSpecifiedNumOfDays(
      int numOfOrdersADay, int delta, int days) {
    for (int i = 0; i < days; i++) {
      System.out.println(" *** DAY " + (i + 1) + " *** ");

      pizzaRestaurantHeadquarters.openRestaurant();

      pizzaChefs.run(employees, warehouse, incomingOrders, pizzaRestaurantHeadquarters);
      deliveryWorkers.run(employees, warehouse, pizzaRestaurantHeadquarters);

      IntStream.range(0, numOfOrdersADay + ThreadLocalRandom.current().nextInt(delta + 1))
          .forEach(
              j ->
                  order(
                      ThreadLocalRandom.current()
                          .nextInt(DELIVERY_TIME_SECONDS_MIN, DELIVERY_TIME_MINUTES_MAX + 1)));

      closeRestaurant();
    }

    return pizzaRestaurantHeadquarters;
  }

  private void order(int timeLimit) {
    double total = PIZZA_PRICES.get(ThreadLocalRandom.current().nextInt(PIZZA_SIZES.size()));

    System.out.println(
        "ORDER #"
            + pizzaRestaurantHeadquarters.getAvailableOrderId()
            + ": You successfully ordered "
            + PIZZA_SIZES.get(ThreadLocalRandom.current().nextInt(PIZZA_SIZES.size()))
            + " "
            + PIZZAS.get(ThreadLocalRandom.current().nextInt(PIZZA_SIZES.size()))
            + ".");

    System.out.println(
        "NEW! We pledge to deliver your pizza in "
            + timeLimit
            + " seconds or less. If the time taken to deliver the pizza is more than "
            + timeLimit
            + " seconds, the pie comes free.");

    System.out.printf("Your total is: $%.2f.\n", total);

    Order order = new Order(pizzaRestaurantHeadquarters.getAvailableOrderId(), timeLimit, total);
    pizzaRestaurantHeadquarters.updateCurrentOrderId();
    incomingOrders.order(order);
  }

  private void closeRestaurant() {
    pizzaRestaurantHeadquarters.closeRestaurant();

    while (!incomingOrders.areThereAnyOrders()) {
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

    for (int i = 0; i < 5; i++) {
      if (!pizzaRestaurantHeadquarters.areAllPizzaChefsFinishedWork()
          || !pizzaRestaurantHeadquarters.areAllDeliveryWorkersFinishedWork()) {

        try {
          Thread.sleep(WAITING_TIME_MILLISECONDS);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }

    pizzaRestaurantHeadquarters.addOneDayToNumOfDaysPassedSinceTheBeginningOfTheWeek();

    if (pizzaRestaurantHeadquarters.getNumOfDaysPassedSinceTheBeginningOfTheWeek() >= 7) {
      analyzeBusinessPerformance();
      pizzaRestaurantHeadquarters.startNextWeek();
    }
  }

  private void analyzeBusinessPerformance() {
    System.out.println("=== YOUR WEEKLY BUSINESS REPORT ===");

    if (pizzaRestaurantHeadquarters.getNumOfCompletedOrders() / 7
        < pizzaRestaurantHeadquarters.getNumOfPizzaChefs()) {
      System.out.println(
          "You should find new customers and increase sales or fire "
              + (pizzaRestaurantHeadquarters.getNumOfPizzaChefs()
                  - pizzaRestaurantHeadquarters.getNumOfCompletedOrders() / 7)
              + " pizza chef(s).");
    }

    if (warehouse.getCapacity() < pizzaRestaurantHeadquarters.getNumOfCompletedOrders() / 7) {
      System.out.println(
          "You should increase warehouse storage capacity by at least "
              + (pizzaRestaurantHeadquarters.getNumOfCompletedOrders() / 7
                  - warehouse.getCapacity())
              + " meters.");
    } else {
      printIdOfWorkersToBeFired(
          pizzaRestaurantHeadquarters.getPizzaChefsJobPerformance(), "pizza chef");
      printIdOfWorkersToBeFired(
          pizzaRestaurantHeadquarters.getPizzaChefsJobPerformance(), "delivery worker");
    }
  }

  private void printIdOfWorkersToBeFired(
      Map<Integer, Integer> workersJobPerformance, String workerPosition) {
    List<Integer> workersToBeFired =
        workersJobPerformance.entrySet().stream()
            .filter(entry -> entry.getValue() < 0)
            .map(Entry::getKey)
            .collect(Collectors.toList());

    if (!workersToBeFired.isEmpty()) {
      System.out.print("You should fire " + workerPosition + "s with IDs: ");

      for (Integer pizzaChefId : workersToBeFired) {
        System.out.print(pizzaChefId + " ");
      }

      System.out.println();
    }
  }
}
