package ru.nsu.fit.oop.arturploter.task_2_2_1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameObjectTest {
  private GameObject gameObject;

  @BeforeEach
  void setUp() {
    Point2D position = new Point2D(0, 0);
    gameObject = new GameObject(position, 25, 25);
  }

  @Test
  void gameObjectPositionChanges_ShouldHaveCorrectPosition() {
    Point2D position = new Point2D(1, 1);
    gameObject.setPosition(position);

    assertEquals(position, gameObject.getPosition());
  }

  @Test
  void gameObjectShouldCollideWithItself() {
    assertTrue(gameObject.doesCollideWith(gameObject));
  }

  @Test
  void gameObjectShouldHaveCorrectBoundary() {
    Rectangle2D boundary =
        new Rectangle2D(gameObject.getPosition().getX(), gameObject.getPosition().getY(), 25, 25);

    assertEquals(boundary, gameObject.getBoundary());
  }
}
