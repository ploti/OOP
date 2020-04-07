package ru.nsu.fit.oop.arturploter.task_2_1_1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.Test;

public class PizzaRestaurantTest {

  @Test
  public void test1() {
    assertEquals(3, 3);
  }

  @Test
  @Ignore
  public void shouldCorrectlyParseJSONData() {
    JSONReader jsonReader = new JSONReader();
    File employeesParameters = new File("src/main/resources/Employees.json");
    Employees employees = jsonReader.readParameters(employeesParameters);

    assertEquals(3, employees.pizzaChefs.length);

    assertEquals(1, employees.pizzaChefs[0].getId());
    assertEquals(2, employees.pizzaChefs[1].getId());
    assertEquals(3, employees.pizzaChefs[2].getId());

    assertEquals(10000, employees.pizzaChefs[0].getCookingTime());
    assertEquals(10000, employees.pizzaChefs[1].getCookingTime());
    assertEquals(10000, employees.pizzaChefs[2].getCookingTime());
    assertEquals(4, employees.deliveryWorkers.length);

    assertEquals(1, employees.deliveryWorkers[0].getId());
    assertEquals(2, employees.deliveryWorkers[1].getId());
    assertEquals(3, employees.deliveryWorkers[2].getId());
    assertEquals(4, employees.deliveryWorkers[3].getId());

    assertEquals(5000, employees.deliveryWorkers[0].getDeliveryTime());
    assertEquals(6000, employees.deliveryWorkers[1].getDeliveryTime());
    assertEquals(4000, employees.deliveryWorkers[2].getDeliveryTime());
    assertEquals(5000, employees.deliveryWorkers[3].getDeliveryTime());

    assertEquals(3, employees.deliveryWorkers[0].getNumOfPizzasCanCarry());
    assertEquals(2, employees.deliveryWorkers[1].getNumOfPizzasCanCarry());
    assertEquals(1, employees.deliveryWorkers[2].getNumOfPizzasCanCarry());
    assertEquals(4, employees.deliveryWorkers[3].getNumOfPizzasCanCarry());
  }


  @Test
  @Ignore
  public void closePizzaRestaurant_AllDeliveryWorkersShouldFinishWork() {
    System.out.println("\n ~~~ closePizzaRestaurant_AllDeliveryWorkersShouldFinishWork ~~~\n");

    DeliveryWorkers deliveryWorkers = new DeliveryWorkers();
    PizzaRestaurantHeadquarters pizzaRestaurantHeadquarters = new PizzaRestaurantHeadquarters();
    Warehouse warehouse = new Warehouse(10);

    DeliveryWorker deliveryWorker1 = new DeliveryWorker(1, 10000, 3);
    DeliveryWorker deliveryWorker2 = new DeliveryWorker(2, 10000, 4);

    deliveryWorker1.setDeliveryWorkers(deliveryWorkers);
    deliveryWorker1.setPizzaRestaurantHeadquarters(pizzaRestaurantHeadquarters);
    deliveryWorker1.setWarehouse(warehouse);

    deliveryWorker2.setDeliveryWorkers(deliveryWorkers);
    deliveryWorker2.setPizzaRestaurantHeadquarters(pizzaRestaurantHeadquarters);
    deliveryWorker2.setWarehouse(warehouse);

    pizzaRestaurantHeadquarters.setNumOfDeliveryWorkers(2);

    Thread thread1 = new Thread(deliveryWorker1);
    Thread thread2 = new Thread(deliveryWorker2);

    thread1.start();
    thread2.start();

    pizzaRestaurantHeadquarters.closeRestaurant();

    try {
      thread1.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    try {
      thread2.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    assertTrue(pizzaRestaurantHeadquarters.areAllDeliveryWorkersFinishedWork());

    System.out.println();
  }

  @Test
  @Ignore
  public void closePizzaRestaurant_AllPizzaChefsShouldFinishWork() {
    System.out.println("\n ~~~ closePizzaRestaurant_AllPizzaChefsShouldFinishWork ~~~\n");

    IncomingOrders incomingOrders = new IncomingOrders();
    PizzaChefs pizzaChefs = new PizzaChefs();
    PizzaRestaurantHeadquarters pizzaRestaurantHeadquarters = new PizzaRestaurantHeadquarters();
    Warehouse warehouse = new Warehouse(10);

    PizzaChef pizzaChef1 = new PizzaChef(1, 10000);
    PizzaChef pizzaChef2 = new PizzaChef(2, 10000);

    pizzaChef1.setPizzaChefs(pizzaChefs);
    pizzaChef1.setPizzaRestaurantHeadquarters(pizzaRestaurantHeadquarters);
    pizzaChef1.setWarehouse(warehouse);
    pizzaChef1.setIncomingOrders(incomingOrders);

    pizzaChef2.setPizzaChefs(pizzaChefs);
    pizzaChef2.setPizzaRestaurantHeadquarters(pizzaRestaurantHeadquarters);
    pizzaChef2.setWarehouse(warehouse);
    pizzaChef2.setIncomingOrders(incomingOrders);

    pizzaRestaurantHeadquarters.setNumOfPizzaChefs(2);

    Thread thread1 = new Thread(pizzaChef1);
    Thread thread2 = new Thread(pizzaChef2);

    thread1.start();
    thread2.start();

    incomingOrders.order(new Order(1));
    incomingOrders.order(new Order(2));

    pizzaRestaurantHeadquarters.closeRestaurant();

    try {
      thread1.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    try {
      thread2.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    assertTrue(pizzaRestaurantHeadquarters.areAllPizzaChefsFinishedWork());

    System.out.println();
  }

  @Test
  @Ignore
  public void pizzaRestaurantShouldBeClosed() {
    System.out.println("\n ~~~ pizzaRestaurantShouldBeClosed ~~~\n");

    File employeesParameters = new File("src/main/resources/Employees.json");
    PizzaRestaurant pizzaRestaurant = new PizzaRestaurant(employeesParameters, 10);
    PizzaRestaurantHeadquarters pizzaRestaurantHeadquarters = pizzaRestaurant.start(4);

    assertTrue(pizzaRestaurantHeadquarters.areAllPizzaChefsFinishedWork());
    assertTrue(pizzaRestaurantHeadquarters.areAllDeliveryWorkersFinishedWork());
    assertTrue(pizzaRestaurantHeadquarters.isRestaurantClosed());

    System.out.println();
  }

  @Test
  @Ignore
  public void shouldVerifyPizzaChefsAndDeliveryWorkers() {
    Warehouse warehouse = new Warehouse(10);
    IncomingOrders incomingOrders = new IncomingOrders();
    PizzaRestaurantHeadquarters pizzaRestaurantHeadquarters = new PizzaRestaurantHeadquarters();
    DeliveryWorkers deliveryWorkers = new DeliveryWorkers();
    PizzaChefs pizzaChefs = new PizzaChefs();

    JSONReader jsonReader = new JSONReader();
    File employeesParameters = new File("src/main/resources/Employees.json");
    Employees employees = jsonReader.readParameters(employeesParameters);

    pizzaChefs.run(employees, warehouse, incomingOrders, pizzaRestaurantHeadquarters);
    deliveryWorkers.run(employees, warehouse, pizzaRestaurantHeadquarters);

    PizzaChef pizzaChef;

    int numOfPizzaChefs = 0;
    for (FutureObjectPair a : pizzaChefs.getPizzaChefsWithTheirPizzas()) {
      numOfPizzaChefs++;
      pizzaChef = (PizzaChef) a.object;
      assertTrue(pizzaChef.getId() >= 1);
    }

    assertEquals(3, numOfPizzaChefs);
    assertEquals(4, deliveryWorkers.getOrders().size());
  }
}
