package ru.nsu.fit.oop.arturploter.task_2_2_1;

import java.util.LinkedList;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/** The class represents a snake that can be moved in 4 directions. */
class Snake {
  private final LinkedList<MovingGameObject> body;
  private final int segmentSize;
  private final Color color;
  private MovingGameObject tail;

  private Direction direction = Direction.RIGHT;

  Snake(Point2D head, Point2D tail, int segmentSize, Color color) {
    this.segmentSize = segmentSize;
    this.color = color;

    body = new LinkedList<>();
    body.add(new MovingGameObject(head, segmentSize));
    body.add(new MovingGameObject(tail, segmentSize));
  }

  void move() {
    tail = body.pollLast();

    switch (direction) {
      case UP:
        {
          Point2D newPos = getHead().getPosition().subtract(0, segmentSize);
          body.addFirst(new MovingGameObject(newPos, segmentSize, direction));
          break;
        }
      case DOWN:
        {
          Point2D newPos = getHead().getPosition().add(0, segmentSize);
          body.addFirst(new MovingGameObject(newPos, segmentSize, direction));
          break;
        }
      case LEFT:
        {
          Point2D newPos = getHead().getPosition().subtract(segmentSize, 0);
          body.addFirst(new MovingGameObject(newPos, segmentSize, direction));
          break;
        }
      case RIGHT:
        {
          Point2D newPos = getHead().getPosition().add(segmentSize, 0);
          body.addFirst(new MovingGameObject(newPos, segmentSize, direction));
          break;
        }
    }
  }

  void addSegment() {
    body.add(new MovingGameObject());
  }

  boolean doesCollideWith(GameObject gameObject) {
    for (int i = 0; i < getSize(); i++) {
      if (gameObject.doesCollideWith(body.get(i))) {
        return true;
      }
    }

    return false;
  }

  boolean doesCollideWithItself() {
    for (int i = 1; i < getSize(); i++) {
      if (getHead().getPosition().equals(getBody(i).getPosition())) {
        return true;
      }
    }

    return false;
  }

  void render(GraphicsContext graphicsContext) {
    graphicsContext.setFill(color);
    graphicsContext.fillRect(
        getHead().getPosition().getX(), getHead().getPosition().getY(), 25, 25);

    graphicsContext.setFill(color);
    graphicsContext.fillRect(
        getNeck().getPosition().getX(),
        getNeck().getPosition().getY(),
        Playfield.PIXEL_SIZE,
        Playfield.PIXEL_SIZE);

    if (tail != null) {
      graphicsContext.setFill(Color.valueOf("#161616"));
      tail.render(graphicsContext);
    }
  }

  void removeSnake(GraphicsContext graphicsContext) {
    graphicsContext.setFill(Color.valueOf("#161616"));

    for (int i = 0; i < body.size(); i++) {
      if (i > 0) {
        graphicsContext.fillRect(
            body.get(i).getPosition().getX(),
            body.get(i).getPosition().getY(),
            Playfield.PIXEL_SIZE,
            Playfield.PIXEL_SIZE);
      }
    }
  }

  MovingGameObject getHead() {
    return body.getFirst();
  }

  MovingGameObject getBody(int index) {
    return body.get(index);
  }

  int getSize() {
    return body.size();
  }

  Direction getDirection() {
    return direction;
  }

  void setDirection(Direction direction) {
    this.direction = direction;
  }

  private MovingGameObject getNeck() {
    return body.get(1);
  }

  boolean hasSegmentAt(Point2D nextPoint) {
    return body.stream().anyMatch(segment -> segment.getPosition().equals(nextPoint));
  }

  void removeSegment(int i, GraphicsContext graphicsContext) {
    MovingGameObject segment = body.remove(i);
    graphicsContext.setFill(Color.valueOf("#161616"));
    graphicsContext.fillRect(
        segment.getPosition().getX(),
        segment.getPosition().getY(),
        Playfield.PIXEL_SIZE,
        Playfield.PIXEL_SIZE);
  }

  LinkedList<MovingGameObject> getBody() {
    return body;
  }
}
