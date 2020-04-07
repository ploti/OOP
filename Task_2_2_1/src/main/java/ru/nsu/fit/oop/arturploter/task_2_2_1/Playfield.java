package ru.nsu.fit.oop.arturploter.task_2_2_1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

class Playfield {

  private static final double WIDTH = GameView.WIDTH / GameObject.SIZE;

  private static final double HEIGHT = GameView.HEIGHT / GameObject.SIZE;

  private final Random random;

  private final List<Apple> apples;

  private final List<Obstacle> obstacles;

  private final Snake snake;

  private final BodyPart head;

  private final ScoreView scoreView;

  private WhiteApple whiteApple;

  private int score;

  private int finalScore;
  private int applesEaten;

  private boolean isWhiteApple;

  private boolean isNewObstacle;

  private int numOfObstacles;

  private boolean isGodMode;

  private Timeline timeBeforeWhiteApple;

  private Timeline whiteAppleLasting;

  Playfield() {

    scoreView = new ScoreView();
    apples = new ArrayList<>();
    obstacles = new ArrayList<>();
    score = 0;
    applesEaten = 0;
    snake = new Snake();
    random = new Random();
    head = snake.getHead();
    whiteApple = null;
    isWhiteApple = false;
    isGodMode = false;
    isNewObstacle = true;
    numOfObstacles = Obstacle.OBSTACLES_START_NUMBER;
  }

  State checkCollision() {
    double headPosX;
    double headPosY;
    double tempPosX;
    double tempPosY;

    headPosX = head.getPosX();
    headPosY = head.getPosY();

    for (int i = 1; i < snake.getSize(); ++i) {

      tempPosX = snake.getBodyPart(i).getPosX();
      tempPosY = snake.getBodyPart(i).getPosY();

      if (tempPosX == headPosX && tempPosY == headPosY) {
        finalScore = score;
        reset();
        return State.Finished;
      }
    }

    if (!isGodMode) {

      for (Obstacle obstacle : obstacles) {

        tempPosX = obstacle.getPosX();
        tempPosY = obstacle.getPosY();

        if (tempPosX == headPosX && tempPosY == headPosY) {
          finalScore = score;
          reset();
          return State.Finished;
        }
      }
    }

    return Controller.getState();
  }

  void updateObstacles() {

    if (applesEaten % 10 == 0 && applesEaten != 0 && !isNewObstacle) {
      isNewObstacle = true;
      numOfObstacles++;
    }

    while (obstacles.size() < numOfObstacles && isNewObstacle) {
      placeObstacles();
    }
  }

  private void placeObstacles() {

    double obstaclePosX = 0;
    double obstaclePosY = 0;
    double tempPosX;
    double tempPosY;
    double headX = snake.getHead().getPosX();
    double headY = snake.getHead().getPosY();

    boolean collision = true;
    boolean tempSnake;
    boolean tempApple;

    while (collision) {

      tempSnake = false;
      tempApple = false;
      obstaclePosX = (random.nextInt((int) WIDTH) * GameObject.SIZE) + GameObject.SIZE / 2;
      obstaclePosY = (random.nextInt((int) HEIGHT) * GameObject.SIZE) + GameObject.SIZE / 2;

      for (int i = 0; i < snake.getSize(); ++i) {

        tempPosX = snake.getBodyPart(i).getPosX();
        tempPosY = snake.getBodyPart(i).getPosY();

        if (tempPosX == obstaclePosX && tempPosY == obstaclePosY) {
          break;
        }

        if (obstaclePosX == headX) {
          if (Math.abs(obstaclePosY - headY) < 8 * GameObject.SIZE) {
            break;
          }
        } else if (obstaclePosY == headY) {
          if (Math.abs(obstaclePosX - headX) < 8 * GameObject.SIZE) {
            break;
          }
        }

        if (i == snake.getSize() - 1) {
          tempSnake = true;
        }
      }

      if (tempSnake) {

        if (apples.size() == 0) {
          tempApple = true;
        } else {

          for (int i = 0; i < apples.size(); ++i) {

            tempPosX = apples.get(i).getPosX();
            tempPosY = apples.get(i).getPosY();

            if (tempPosX == obstaclePosX && tempPosY == obstaclePosY) {
              break;
            }

            if (i == apples.size() - 1) {
              tempApple = true;
            }
          }
        }

        if (isWhiteApple) {
          if (obstaclePosX == whiteApple.getPosX() && obstaclePosY == whiteApple.getPosY()) {
            continue;
          }
        }

        if (tempApple) {
          collision = false;
        }
      }
    }

    addObstacle(obstaclePosX, obstaclePosY);
  }

  private void addObstacle(double X, double Y) {
    obstacles.add(new Obstacle(X, Y));
  }

  void checkEaten() {

    double headX, headY, foodX, foodY;
    headX = head.getPosX();
    headY = head.getPosY();

    if (isWhiteApple) {

      if (whiteApple.getPosX() == headX && whiteApple.getPosY() == headY) {
        removeSuperFruit();
        ++applesEaten;
        score += 3;
        isGodMode = true;
        whiteAppleLasting.stop();
        if (timeBeforeWhiteApple != null) {
          timeBeforeWhiteApple.stop();
        }
        timeBeforeWhiteApple =
            new Timeline(
                new KeyFrame(
                    Duration.millis(WhiteApple.SUPER_STATE_TIME), lambda -> isGodMode = false));
        timeBeforeWhiteApple.play();
        isNewObstacle = false;
        return;
      }
    }

    for (int i = 0; i < apples.size(); ++i) {

      foodX = apples.get(i).getPosX();
      foodY = apples.get(i).getPosY();

      if (foodX == headX && foodY == headY) {

        removeFruit(i);
        addLength();
        ++applesEaten;

        if (isGodMode) score += 2;
        else ++score;

        isNewObstacle = false;
      }
    }
  }

  void updateFruit() {

    double foodX, foodY, sFoodX = -1, sFoodY = -1;
    double[] place;

    if (apples.size() <= 0) {

      if (applesEaten % 10 == 0 && applesEaten != 0 && !isWhiteApple) {
        isWhiteApple = true;
        place = placeFruit();
        sFoodX = place[0];
        sFoodY = place[1];
      }

      do {
        place = placeFruit();
        foodX = place[0];
        foodY = place[1];
      } while (foodX == sFoodX && foodY == sFoodY);

      addFruit(foodX, foodY, sFoodX, sFoodY);
    }
  }

  private double[] placeFruit() {

    double[] point = new double[2];

    double helpX, helpY, foodX = 0, foodY = 0;
    boolean helpS, helpO;
    boolean collision = true;

    while (collision) {

      helpS = helpO = false;
      foodX = (random.nextInt((int) WIDTH) * GameObject.SIZE) + GameObject.SIZE / 2;
      foodY = (random.nextInt((int) HEIGHT) * GameObject.SIZE) + GameObject.SIZE / 2;

      for (int i = 0; i < snake.getSize(); ++i) {

        helpX = snake.getBodyPart(i).getPosX();
        helpY = snake.getBodyPart(i).getPosY();

        if (helpX == foodX && helpY == foodY) {
          break;
        }

        if (i == snake.getSize() - 1) {
          helpS = true;
        }
      }

      if (helpS) {

        if (obstacles.size() == 0) {
          helpO = true;
        } else {

          for (int i = 0; i < obstacles.size(); ++i) {

            helpX = obstacles.get(i).getPosX();
            helpY = obstacles.get(i).getPosY();

            if (foodX == helpX && foodY == helpY) {
              break;
            }

            if (i == obstacles.size() - 1) {
              helpO = true;
            }
          }
        }
        if (helpO) {
          collision = false;
        }
      }
    }
    point[0] = foodX;
    point[1] = foodY;
    return point;
  }

  private void addFruit(double foodX, double foodY, double sFoodX, double sFoodY) {

    if (sFoodX != -1 && sFoodY != -1) {
      whiteApple = new WhiteApple(sFoodX, sFoodY);
      whiteAppleLasting =
          new Timeline(
              new KeyFrame(
                  Duration.millis(WhiteApple.ON_BOARD_TIME), lambda -> removeSuperFruit()));
      whiteAppleLasting.play();
    }
    apples.add(new Apple(foodX, foodY));
  }

  private void removeFruit(int i) {
    apples.remove(i);
  }

  private void removeSuperFruit() {
    isWhiteApple = false;
    whiteApple = null;
  }

  void updateScore() {
    scoreView.addScore(score);
  }

  private void addLength() {
    BodyPart b1 = snake.getBodyPart(snake.getSize() - 1),
        b2 = snake.getBodyPart(snake.getSize() - 2);
    if (b1.getPosX() > b2.getPosX())
      snake.addBodyPart(b1.getPosX() + GameObject.SIZE, b1.getPosY());
    else if (b1.getPosX() < b2.getPosX())
      snake.addBodyPart(b1.getPosX() - GameObject.SIZE, b1.getPosY());
    else if (b1.getPosY() >= b2.getPosY())
      snake.addBodyPart(b1.getPosX(), b1.getPosY() + GameObject.SIZE);
  }

  private void reset() {
    snake.setStart();
    obstacles.clear();
    apples.clear();
    score = applesEaten = 0;
    isNewObstacle = true;
    isGodMode = false;
    removeSuperFruit();
    numOfObstacles = Obstacle.OBSTACLES_START_NUMBER;
  }

  List<Apple> getApples() {
    return apples;
  }

  WhiteApple getWhiteApple() {
    return whiteApple;
  }

  List<Obstacle> getObstacles() {
    return obstacles;
  }

  Snake getSnake() {
    return snake;
  }

  ScoreView getScoreView() {
    return scoreView;
  }

  int getScore() {
    return score;
  }

  int getFinalScore() {
    return finalScore;
  }

  boolean getGodMode() {
    return isGodMode;
  }
}
