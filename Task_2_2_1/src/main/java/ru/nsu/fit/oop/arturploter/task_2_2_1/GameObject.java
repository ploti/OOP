package ru.nsu.fit.oop.arturploter.task_2_2_1;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;

/** The class represents all the objects in the game */
class GameObject {
  Point2D position;
  private double width;
  private double height;

  GameObject() {
    position = new Point2D(0, 0);
  }

  GameObject(double width, double height) {
    position = new Point2D(0, 0);
    this.width = width;
    this.height = height;
  }

  GameObject(Point2D position, double width, double height) {
    this.position = position;
    this.width = width;
    this.height = height;
  }

  GameObject(Point2D position, double size) {
    this.position = position;
    width = size;
    height = size;
  }

  void render(GraphicsContext graphicsContext) {
    graphicsContext.fillRect(position.getX(), position.getY(), width, height);
  }

  boolean doesCollideWith(GameObject gameObject) {
    return this.getBoundary().intersects(gameObject.getBoundary());
  }

  Point2D getPosition() {
    return position;
  }

  void setPosition(Point2D position) {
    this.position = position;
  }

  Rectangle2D getBoundary() {
    return new Rectangle2D(position.getX(), position.getY(), width, height);
  }
}
