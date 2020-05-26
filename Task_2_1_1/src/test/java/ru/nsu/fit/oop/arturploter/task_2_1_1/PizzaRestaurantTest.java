package ru.nsu.fit.oop.arturploter.task_2_1_1;

import java.io.File;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class PizzaRestaurantTest {

  @Ignore
  @Test
  public void shouldCorrectlyParseJSONData() {
    JSONReader jsonReader = new JSONReader();
    File employeesParameters = new File("src/main/resources/Employees.json");
    Employees employees = jsonReader.readParameters(employeesParameters);

    Assert.assertEquals(3, employees.pizzaChefs.length);

    Assert.assertEquals(1, employees.pizzaChefs[0].getId());
    Assert.assertEquals(2, employees.pizzaChefs[1].getId());
    Assert.assertEquals(3, employees.pizzaChefs[2].getId());

    Assert.assertEquals(10000, employees.pizzaChefs[0].getCookingTime());
    Assert.assertEquals(10000, employees.pizzaChefs[1].getCookingTime());
    Assert.assertEquals(10000, employees.pizzaChefs[2].getCookingTime());
    Assert.assertEquals(4, employees.deliveryWorkers.length);

    Assert.assertEquals(1, employees.deliveryWorkers[0].getId());
    Assert.assertEquals(2, employees.deliveryWorkers[1].getId());
    Assert.assertEquals(3, employees.deliveryWorkers[2].getId());
    Assert.assertEquals(4, employees.deliveryWorkers[3].getId());

    Assert.assertEquals(5000, employees.deliveryWorkers[0].getDeliveryTime());
    Assert.assertEquals(6000, employees.deliveryWorkers[1].getDeliveryTime());
    Assert.assertEquals(4000, employees.deliveryWorkers[2].getDeliveryTime());
    Assert.assertEquals(5000, employees.deliveryWorkers[3].getDeliveryTime());

    Assert.assertEquals(3, employees.deliveryWorkers[0].getNumOfPizzasCanCarry());
    Assert.assertEquals(2, employees.deliveryWorkers[1].getNumOfPizzasCanCarry());
    Assert.assertEquals(1, employees.deliveryWorkers[2].getNumOfPizzasCanCarry());
    Assert.assertEquals(4, employees.deliveryWorkers[3].getNumOfPizzasCanCarry());
  }

  @Ignore
  @Test
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

    Assert.assertTrue(pizzaRestaurantHeadquarters.areAllDeliveryWorkersFinishedWork());

    System.out.println();
  }

  @Ignore
  @Test
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

    Assert.assertTrue(pizzaRestaurantHeadquarters.areAllPizzaChefsFinishedWork());

    System.out.println();
  }

  @Test
  public void shouldStartNewWeekAndSuggestToFireAlmostAllOfTheStuff() {
    System.out.println("\n ~~~ pizzaRestaurantShouldBeClosed ~~~\n");

    File employeesParameters = new File("src/main/resources/Employees.json");
    PizzaRestaurant pizzaRestaurant = new PizzaRestaurant(employeesParameters, 10);

    PizzaRestaurantHeadquarters pizzaRestaurantHeadquarters =
        pizzaRestaurant.runPizzaRestaurantForSpecifiedNumOfDays(5, 1, 7);

    Assert.assertEquals(
        pizzaRestaurantHeadquarters.getNumOfDaysPassedSinceTheBeginningOfTheWeek(), 0);

    System.out.println();
  }

  @Ignore
  @Test
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
      Assert.assertTrue(pizzaChef.getId() >= 1);
    }

    Assert.assertEquals(3, numOfPizzaChefs);
    Assert.assertEquals(4, deliveryWorkers.getOrders().size());
  }
}
