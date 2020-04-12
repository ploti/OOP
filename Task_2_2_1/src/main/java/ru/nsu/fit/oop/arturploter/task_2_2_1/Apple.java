package ru.nsu.fit.oop.arturploter.task_2_2_1;

import static ru.nsu.fit.oop.arturploter.task_2_2_1.Playfield.PIXEL_SIZE;

import java.util.Random;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * The class represents an apple that is placed on the playfield one at a time
 * and can be eaten by a snake.
 */
class Apple extends GameObject {
  Apple(double width, double height) {
    super(width, height);
  }

  @Override
  void render(GraphicsContext graphicsContext) {
    graphicsContext.setFill(Color.rgb(255, 75, 40));
    graphicsContext.fillOval(position.getX(), position.getY(), 23, 23);
  }

  void place(int width, int height, Iterable<Obstacle> obstacles) {
    Point2D applePosition = new Point2D(0, 0);

    boolean collision = true;
    while (collision) {
      Random random = new Random();
      applePosition =
          new Point2D(
              random.nextInt((width) / PIXEL_SIZE) * PIXEL_SIZE,
              random.nextInt((height) / PIXEL_SIZE) * PIXEL_SIZE);

      for (Obstacle obstacle : obstacles) {
        collision = true;

        if (applePosition.equals(obstacle.getPosition())) {
          break;
        }

        collision = false;
      }
    }

    setPosition(applePosition);
  }
}
