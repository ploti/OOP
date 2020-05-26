package ru.nsu.fit.oop.arturploter.task_2_1_1;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class DeliveryWorker implements Runnable {
  private static final int WAITING_TIME_MILLISECONDS = 1000;

  @JsonProperty("id")
  private final int id;

  @JsonProperty("howManyPizzasCanCarry")
  private final int numOfPizzasCanCarry;

  private final List<Order> bag;

  @JsonProperty("deliveryTime")
  private final int deliveryTime;

  private Warehouse warehouse;
  private PizzaRestaurantHeadquarters pizzaRestaurantHeadquarters;
  private DeliveryWorkers deliveryWorkers;

  DeliveryWorker(
      @JsonProperty("id") int id,
      @JsonProperty("deliveryTime") int deliveryTime,
      @JsonProperty("howManyPizzasCanCarry") int numOfPizzasCanCarry) {

    this.id = id;
    this.deliveryTime = deliveryTime;
    this.numOfPizzasCanCarry = numOfPizzasCanCarry;
    this.bag = new ArrayList<>();
  }

  void setWarehouse(Warehouse warehouse) {
    this.warehouse = warehouse;
  }

  void setPizzaRestaurantHeadquarters(PizzaRestaurantHeadquarters pizzaRestaurantHeadquarters) {
    this.pizzaRestaurantHeadquarters = pizzaRestaurantHeadquarters;
  }

  void setDeliveryWorkers(DeliveryWorkers deliveryWorkers) {
    this.deliveryWorkers = deliveryWorkers;
  }

  @Override
  public void run() {
    while (!pizzaRestaurantHeadquarters.isRestaurantClosed()
        || !(warehouse.numOfItemsInWarehouse() == 0
            && pizzaRestaurantHeadquarters.areAllPizzaChefsFinishedWork())) {

      boolean isClosed = false;

      deliveryWorkers.lock.lock();
      try {
        if (pizzaRestaurantHeadquarters.isRestaurantClosed()
            && warehouse.numOfItemsInWarehouse() == 0
            && pizzaRestaurantHeadquarters.areAllPizzaChefsFinishedWork()) {

          break;
        }

        for (int i = 0; i < numOfPizzasCanCarry; i++) {
          Order order = null;

          if (bag.size() != 0) {
            try {
              order = warehouse.pickItemForDelivery(WAITING_TIME_MILLISECONDS);
              order.setDeliveryWorkerId(id);

              bag.add(order);
              System.out.println(
                  "DELIVERY WORKER #"
                      + id
                      + " picked up ORDER #"
                      + order.getId()
                      + ". He has "
                      + bag.size()
                      + " pizza(s) in the bag.");
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          } else {
            while (bag.size() == 0) {
              if (pizzaRestaurantHeadquarters.isRestaurantClosed()
                  && warehouse.numOfItemsInWarehouse() == 0
                  && pizzaRestaurantHeadquarters.areAllPizzaChefsFinishedWork()) {

                System.out.println(
                    "WE'RE CLOSED! BUZZ OFF, JOKER! EAT YOUR SHITTY FAST FOOD AT MCDONALD'S!");

                isClosed = true;
                break;
              }

              order = warehouse.pickItemForDelivery(WAITING_TIME_MILLISECONDS);

              if (order != null) {
                bag.add(order);
              }
            }

            if (isClosed) {
              break;
            }

            System.out.println(
                "DELIVERY WORKER #"
                    + id
                    + " picked up the ORDER #"
                    + Objects.requireNonNull(order).getId()
                    + ". He has "
                    + bag.size()
                    + " shitty pizza(s) in the bag.");
          }
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      } finally {
        deliveryWorkers.lock.unlock();
      }

      if (isClosed) {
        break;
      }

      System.out.println("DELIVERY WORKER #" + id + " almost made it!");

      try {
        for (Order order : bag) {
          Thread.sleep(deliveryTime);
          System.out.println(
              "DELIVERY WORKER #" + id + " left your shitty pizza(s) in the trash. Bon appetit!");

          pizzaRestaurantHeadquarters.completeOrder(order);
        }

        System.out.println("DELIVERY WORKER #" + id + " delivered all orders.");
        Thread.sleep(deliveryTime);
        bag.clear();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    pizzaRestaurantHeadquarters.endShiftForDeliveryWorker();
    System.out.println("DELIVERY WORKER #" + id + " is done for today.");
  }

  int getId() {
    return id;
  }

  int getDeliveryTime() {
    return deliveryTime;
  }

  int getNumOfPizzasCanCarry() {
    return numOfPizzasCanCarry;
  }
}
