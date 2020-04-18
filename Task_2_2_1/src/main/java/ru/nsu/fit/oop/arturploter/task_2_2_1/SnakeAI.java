package ru.nsu.fit.oop.arturploter.task_2_2_1;

import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Point2D;

/** The class represents snake AI. */
class SnakeAI {
  private static final int OBSTACLE = -1;
  private static final int GRID_WIDTH = Playfield.WIDTH / Playfield.PIXEL_SIZE;
  private static final int GRID_HEIGHT = Playfield.HEIGHT / Playfield.PIXEL_SIZE;

  private final int[][] distanceGrid;
  private final Snake snake;
  private final Snake playersSnake;
  private final List<Point2D> pathToApple;

  SnakeAI(Snake snake, Snake playersSnake) {
    this.distanceGrid = new int[GRID_WIDTH][GRID_HEIGHT];
    this.playersSnake = playersSnake;
    this.snake = snake;
    pathToApple = new ArrayList<>();
  }

  void initializeGrid(Iterable<Obstacle> obstacles) {
    for (int x = 0; x < GRID_WIDTH; x++) {
      for (int y = 0; y < GRID_HEIGHT; y++) {
        distanceGrid[x][y] = 0;
      }
    }

    for (Obstacle obstacle : obstacles) {
      distanceGrid[(int) obstacle.getPosition().getX() / Playfield.PIXEL_SIZE][
              (int) obstacle.getPosition().getY() / Playfield.PIXEL_SIZE] =
          OBSTACLE;
    }
  }

  private void updateGrid() {
    addSnakeBodySegmentsAsObstacles(snake.getBody());
    addSnakeBodySegmentsAsObstacles(playersSnake.getBody());
  }

  /** Updates the bot state */
  void update(Iterable<Obstacle> obstacles, Point2D applePosition) {
    //    updateGrid();
    updatePath(obstacles, applePosition);

    if (pathToApple.size() > 0) {
      followPath();
    }
  }

  private void addSnakeBodySegmentsAsObstacles(Iterable<MovingGameObject> body) {
    body.forEach(
        segment ->
            distanceGrid[(int) segment.getPosition().getX() / Playfield.PIXEL_SIZE][
                    (int) segment.getPosition().getY() / Playfield.PIXEL_SIZE] =
                OBSTACLE);
  }

  private void updatePath(Iterable<Obstacle> obstacles, Point2D applePosition) {
    if (pathToApple.size() > 0) {
      for (int x = 0; x < GRID_WIDTH; x++) {
        for (int y = 0; y < GRID_HEIGHT; y++) {
          for (Point2D nextPoint : pathToApple) {
            if (nextPoint.getX() == x
                && nextPoint.getY() == y
                && distanceGrid[x][y] == OBSTACLE
                && !snake.hasSegmentAt(
                    new Point2D(x * Playfield.PIXEL_SIZE, y * Playfield.PIXEL_SIZE))) {

              findPathToApple(obstacles, applePosition);
              break;
            }
          }
        }
      }
    }
  }

  private void resetGrid(Iterable<Obstacle> obstacles) {
    initializeGrid(obstacles);
    updateGrid();
  }

  /** Finds the path from the bot's snake to an apple. */
  void findPathToApple(Iterable<Obstacle> obstacles, Point2D applePosition) {
    pathToApple.clear();
    resetGrid(obstacles);

    List<Point2D> points = new ArrayList<>();
    List<Point2D> newPoints = new ArrayList<>();

    points.add(
        new Point2D(
            applePosition.getX() / Playfield.PIXEL_SIZE,
            applePosition.getY() / Playfield.PIXEL_SIZE));

    moveToPoints(points, newPoints, 0, applePosition);

    Point2D closest =
        getClosestPoint(
            (int) snake.getHead().getPosition().getX() / Playfield.PIXEL_SIZE,
            (int) snake.getHead().getPosition().getY() / Playfield.PIXEL_SIZE);

    processPoint((int) closest.getX(), (int) closest.getY());
  }

  private Point2D getClosestPoint(int x, int y) {
    Point2D closest;

    int distanceLeft = Integer.MAX_VALUE;
    int distanceRight = Integer.MAX_VALUE;
    int distanceUp = Integer.MAX_VALUE;
    int distanceDown = Integer.MAX_VALUE;

    if ((x + 1 >= 0 && x + 1 < GRID_WIDTH)
        && (y >= 0 && y < GRID_HEIGHT)
        && distanceGrid[x + 1][y] != OBSTACLE) {
      distanceRight = distanceGrid[x + 1][y];
    }

    if ((x - 1 >= 0 && x - 1 < GRID_WIDTH)
        && (y >= 0 && y < GRID_HEIGHT)
        && distanceGrid[x - 1][y] != OBSTACLE) {
      distanceLeft = distanceGrid[x - 1][y];
    }

    if ((x >= 0 && x < GRID_WIDTH)
        && (y + 1 >= 0 && y + 1 < GRID_HEIGHT)
        && distanceGrid[x][y + 1] != OBSTACLE) {
      distanceDown = distanceGrid[x][y + 1];
    }

    if ((x >= 0 && x < GRID_WIDTH)
        && (y - 1 >= 0 && y - 1 < GRID_HEIGHT)
        && distanceGrid[x][y - 1] != OBSTACLE) {
      distanceUp = distanceGrid[x][y - 1];
    }

    if (distanceRight <= distanceLeft
        && distanceRight <= distanceUp
        && distanceRight <= distanceDown) {

      closest = new Point2D(x + 1, y);
    } else if (distanceLeft <= distanceRight
        && distanceLeft <= distanceUp
        && distanceLeft <= distanceDown) {
      closest = new Point2D(x - 1, y);
    } else if (distanceUp <= distanceLeft
        && distanceUp <= distanceRight
        && distanceUp <= distanceDown) {
      closest = new Point2D(x, y - 1);
    } else {
      closest = new Point2D(x, y + 1);
    }

    return closest;
  }

  /** Processes the point and adds it to the path if it is good. */
  private void processPoint(int x, int y) {
    int distance = distanceGrid[x][y];

    pathToApple.add(new Point2D(x, y));

    if (distance == 0) {
      return;
    }

    if (x + 1 < GRID_WIDTH
        && y < GRID_HEIGHT
        && distanceGrid[x + 1][y] < distance
        && distanceGrid[x + 1][y] != OBSTACLE) {
      processPoint(x + 1, y);
    } else if (x - 1 >= 0
        && x - 1 < GRID_WIDTH
        && y < GRID_HEIGHT
        && distanceGrid[x - 1][y] < distance
        && distanceGrid[x - 1][y] != OBSTACLE) {
      processPoint(x - 1, y);
    } else if (x < GRID_WIDTH
        && y + 1 < GRID_HEIGHT
        && distanceGrid[x][y + 1] < distance
        && distanceGrid[x][y + 1] != OBSTACLE) {
      processPoint(x, y + 1);
    } else if (x < GRID_WIDTH
        && y - 1 >= 0
        && y - 1 < GRID_HEIGHT
        && distanceGrid[x][y - 1] < distance
        && distanceGrid[x][y - 1] != OBSTACLE) {
      processPoint(x, y - 1);
    }
  }

  private void moveToPoints(
      List<Point2D> points, List<Point2D> newPoints, int distance, Point2D applePosition) {
    if (points.size() == 0) {
      return;
    }

    for (Point2D point2D : points) {
      int x = (int) point2D.getX();
      int y = (int) point2D.getY();

      distanceGrid[x][y] = distance;

      if (x + 1 < GRID_WIDTH
          && y < GRID_HEIGHT
          && distanceGrid[x + 1][y] == 0
          && (x + 1 != (applePosition.getX() / Playfield.PIXEL_SIZE)
              || y != (applePosition.getY() / Playfield.PIXEL_SIZE))) {
        Point2D point = new Point2D(x + 1, y);

        if (!newPoints.contains(point)) {
          newPoints.add(point);
        }
      }

      if (x - 1 >= 0
          && x - 1 < GRID_WIDTH
          && y < GRID_HEIGHT
          && distanceGrid[x - 1][y] == 0
          && (x - 1 != (applePosition.getX() / Playfield.PIXEL_SIZE)
              || y != (applePosition.getY() / Playfield.PIXEL_SIZE))) {
        Point2D point = new Point2D(x - 1, y);

        if (!newPoints.contains(point)) {
          newPoints.add(point);
        }
      }

      if (x < GRID_WIDTH
          && y + 1 < GRID_HEIGHT
          && distanceGrid[x][y + 1] == 0
          && (x != (applePosition.getX() / Playfield.PIXEL_SIZE)
              || y + 1 != (applePosition.getY() / Playfield.PIXEL_SIZE))) {
        Point2D point = new Point2D(x, y + 1);

        if (!newPoints.contains(point)) {
          newPoints.add(point);
        }
      }

      if (x < GRID_WIDTH
          && y - 1 >= 0
          && y - 1 < GRID_HEIGHT
          && distanceGrid[x][y - 1] == 0
          && (x != (applePosition.getX() / Playfield.PIXEL_SIZE)
              || y - 1 != (applePosition.getY() / Playfield.PIXEL_SIZE))) {
        Point2D point = new Point2D(x, y - 1);

        if (!newPoints.contains(point)) {
          newPoints.add(point);
        }
      }
    }

    points.clear();

    moveToPoints(newPoints, points, distance + 1, applePosition);
  }

  /** Makes the bot follow the path to an apple. */
  private void followPath() {
    Point2D point = pathToApple.get(0);

    if (point.getY() == (snake.getHead().getPosition().getY() / Playfield.PIXEL_SIZE)) {
      if (point.getX() > (snake.getHead().getPosition().getX() / Playfield.PIXEL_SIZE)) {
        snake.setDirection(Direction.RIGHT);
        pathToApple.remove(0);
      } else if (point.getX() < (snake.getHead().getPosition().getX() / Playfield.PIXEL_SIZE)) {
        snake.setDirection(Direction.LEFT);
        pathToApple.remove(0);
      }
    } else if (point.getX() == (snake.getHead().getPosition().getX() / Playfield.PIXEL_SIZE)) {
      if (point.getY() > (snake.getHead().getPosition().getY() / Playfield.PIXEL_SIZE)) {
        snake.setDirection(Direction.DOWN);
        pathToApple.remove(0);
      } else if (point.getY() < (snake.getHead().getPosition().getY() / Playfield.PIXEL_SIZE)) {
        snake.setDirection(Direction.UP);
        pathToApple.remove(0);
      }
    }
  }
}
