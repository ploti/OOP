package ru.nsu.fit.oop.arturploter.task_2_2_1;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/** The class represents an obstacle. If the snake hits the obstacle, the game ends. */
class Obstacle extends GameObject {

  Obstacle(Point2D position, double width, double height) {
    super(position, width, height);
  }

  @Override
  void render(GraphicsContext graphicsContext) {
    graphicsContext.setFill(Color.WHITE);
    graphicsContext.fillRect(
        position.getX(), position.getY(), Playfield.PIXEL_SIZE, Playfield.PIXEL_SIZE);
  }
}
