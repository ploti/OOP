package ru.nsu.fit.oop.arturploter.task_2_2_1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * The class represents the playfield. It encapsulates all the game logic and contains objects
 * that can be seen on the screen: obstacles, an apple, and a snake.
 */
class Playfield extends Scene {
  static final int PIXEL_SIZE = 25;
  private static final int FOOD_POINT = 1;
  private final int WIDTH = 800;
  private final int HEIGHT = 800;

  private final GraphicsContext graphicsContext;

  private final Apple apple;
  private final List<Obstacle> obstacles;
  private final int numOfObstacles;
  private final GameTimer timer;
  private final EventHandler<KeyEvent> wasdEventHandler = new WASDEventHandler();
  private final boolean isGameOverMessage;
  private Snake snake;
  private int score;
  private boolean isInGame;
  private boolean isPaused;
  private boolean isGameOver;
  private long time;
  private Label pauseLabel;
  private Label gameOverLabel;
  private Label scoreLabel;
  private Label inGameScoreLabel;

  Playfield(Parent root, long time) {
    this(root);
    this.time = time;
  }

  Playfield(Parent root) {
    super(root);

    Canvas canvas = new Canvas(WIDTH, HEIGHT);
    ((Pane) root).getChildren().add(canvas);

    graphicsContext = canvas.getGraphicsContext2D();

    apple = new Apple(PIXEL_SIZE, PIXEL_SIZE);
    obstacles = new ArrayList<>();
    numOfObstacles = 30;

    isInGame = false;
    isPaused = false;
    isGameOver = false;
    isGameOverMessage = true;
    score = 0;

    timer = new GameTimer();

    addEventHandler(KeyEvent.KEY_PRESSED, wasdEventHandler);

    EventHandler<KeyEvent> escEventHandler = new EscEventHandler();
    addEventHandler(KeyEvent.KEY_PRESSED, escEventHandler);

    initLabels();
    initScreen();
  }

  boolean isGameOver() {
    return isGameOverMessage;
  }

  Snake getSnake() {
    return snake;
  }

  private void initLabels() {
    pauseLabel = new Label("Paused");
    pauseLabel.setLayoutX(WIDTH / 2f - 25);
    pauseLabel.setLayoutY(HEIGHT / 2f);

    pauseLabel
        .getStylesheets()
        .add(getClass().getResource("/styles/GeneralStyle.css").toExternalForm());

    gameOverLabel = new Label("Game Over!");
    gameOverLabel.setLayoutX(WIDTH / 2f - 100);
    gameOverLabel.setLayoutY(HEIGHT / 2f - 120);

    gameOverLabel
        .getStylesheets()
        .add(getClass().getResource("/styles/GeneralStyle.css").toExternalForm());

    scoreLabel = new Label();
    scoreLabel.setLayoutX(WIDTH / 2f - 100);
    scoreLabel.setLayoutY(HEIGHT / 2f - 10);

    scoreLabel
        .getStylesheets()
        .add(getClass().getResource("/styles/GeneralStyle.css").toExternalForm());

    inGameScoreLabel = new Label();
    inGameScoreLabel.setId("inGameScoreLabel");
    inGameScoreLabel.setLayoutX(0);
    inGameScoreLabel.setLayoutY(0);

    inGameScoreLabel
        .getStylesheets()
        .add(getClass().getResource("/styles/GeneralStyle.css").toExternalForm());

    ((AnchorPane) getRoot()).getChildren().add(inGameScoreLabel);
  }

  private void initScreen() {
    score = 0;
    inGameScoreLabel.setText("Score: " + score);

    renderBackground();
    initSnake();
    initObstacles();
    apple.place(WIDTH, HEIGHT, obstacles);
    renderGameElements();
  }

  private void renderBackground() {
    graphicsContext.setFill(Color.valueOf("#161616"));
    graphicsContext.fillRect(0, 0, WIDTH, HEIGHT);
  }

  private void initSnake() {
    snake =
        new Snake(
            new Point2D(WIDTH / 2f, HEIGHT / 2f),
            new Point2D(WIDTH / 2f - PIXEL_SIZE, HEIGHT / 2f),
            PIXEL_SIZE);
  }

  private void initObstacles() {
    while (obstacles.size() < numOfObstacles) {
      placeObstacles();
    }
  }

  private void placeObstacles() {
    Point2D obstacle = new Point2D(0, 0);
    Point2D temp;
    Point2D head = snake.getHead().getPosition();

    boolean collision = true;
    boolean tempSnake;

    while (collision) {
      tempSnake = false;

      Random random = new Random();
      obstacle =
          new Point2D(
              random.nextInt(WIDTH / PIXEL_SIZE) * PIXEL_SIZE,
              random.nextInt(HEIGHT / PIXEL_SIZE) * PIXEL_SIZE);

      for (int i = 0; i < snake.getSize(); i++) {
        temp = snake.getBody(i).getPosition();

        if (temp.equals(obstacle)) {
          break;
        }

        if (obstacle.getX() == head.getX()) {
          if (Math.abs(obstacle.getY() - head.getY()) < 8 * PIXEL_SIZE) {
            break;
          }
        } else if (obstacle.getY() == head.getY()) {
          if (Math.abs(obstacle.getX() - head.getX()) < 8 * PIXEL_SIZE) {
            break;
          }
        }

        if (i == snake.getSize() - 1) {
          tempSnake = true;
        }
      }

      if (tempSnake) {
        collision = false;
      }
    }

    addObstacle(obstacle);
  }

  private void addObstacle(Point2D obstacle) {
    obstacles.add(new Obstacle(obstacle, WIDTH, HEIGHT));
  }

  private boolean isSnakeInsidePlayableArea() {
    double posX = snake.getHead().getPosition().getX();
    double posY = snake.getHead().getPosition().getY();

    return posX >= WIDTH || posX < 0 || posY >= HEIGHT || posY < 0;
  }

  private void renderGameElements() {
    snake.render(graphicsContext);
    apple.render(graphicsContext);
    obstacles.forEach(obstacle -> obstacle.render(graphicsContext));
  }

  private void renderGameOverMessage() {
    scoreLabel.setText("Your Score: " + score);
    scoreLabel.setLayoutX(WIDTH / 2f - 100);
    scoreLabel.setLayoutY(HEIGHT / 2f - 70);

    Button restartButton = new Button("Restart");
    restartButton.setLayoutX(WIDTH / 2f - 100);
    restartButton.setLayoutY(HEIGHT / 2f);

    restartButton
        .getStylesheets()
        .add(getClass().getResource("/styles/GameOverStyle.css").toExternalForm());

    Button backButton = new Button("Back");
    backButton.setLayoutX(WIDTH / 2f - 100);
    backButton.setLayoutY(HEIGHT / 2f + 50);
    backButton
        .getStylesheets()
        .add(getClass().getResource("/styles/GameOverStyle.css").toExternalForm());

    backButton.setOnMouseClicked(
        e -> {
          Stage stage = (Stage) getWindow();
          Parent root = null;

          try {
            root = FXMLLoader.load(getClass().getResource("/views/MainMenuView.fxml"));
          } catch (IOException ex) {
            ex.printStackTrace();
          }

          stage.setScene(new Scene(Objects.requireNonNull(root)));
          stage.centerOnScreen();
          stage.show();
        });

    Button exitButton = new Button("Exit");
    exitButton.setLayoutX(WIDTH / 2f - 100);
    exitButton.setLayoutY(HEIGHT / 2f + 100);

    exitButton
        .getStylesheets()
        .add(getClass().getResource("/styles/GameOverStyle.css").toExternalForm());

    exitButton.setOnMouseClicked(e -> System.exit(0));
    restartButton.setOnMouseClicked(
        e -> {
          isGameOver = false;
          ((AnchorPane) getRoot())
              .getChildren()
              .removeAll(gameOverLabel, scoreLabel, restartButton, backButton, exitButton);

          apple.place(WIDTH, HEIGHT, obstacles);
          addEventHandler(KeyEvent.KEY_PRESSED, wasdEventHandler);
          initScreen();
        });

    ((AnchorPane) getRoot())
        .getChildren()
        .addAll(gameOverLabel, scoreLabel, restartButton, backButton, exitButton);
  }

  private boolean doesSnakeCollideWithAnObstacle() {
    for (Obstacle obstacle : obstacles) {
      if (snake.getHead().getPosition().equals(obstacle.getPosition())) {
        return true;
      }
    }

    return false;
  }

  private class GameTimer extends AnimationTimer {
    private long lastUpdate = 0;

    @Override
    public void start() {
      super.start();
      isInGame = true;
    }

    @Override
    public void handle(long now) {
      if (now - lastUpdate >= time) {
        addEventHandler(KeyEvent.KEY_PRESSED, wasdEventHandler);
        lastUpdate = now;

        snake.move();

        if (snake.getHead().doesCollideWith(apple)) {
          do {
            apple.place(WIDTH, HEIGHT, obstacles);
          } while (snake.doesCollideWith(apple));

          score += FOOD_POINT;
          snake.addSegment();

          inGameScoreLabel.setText("Score: " + score);
        }

        renderGameElements();

        if (snake.doesCollideWithItself()
            || isSnakeInsidePlayableArea()
            || doesSnakeCollideWithAnObstacle()) {
          isGameOver = true;
        }

        if (isGameOver) {
          this.stop();
          renderGameOverMessage();
        }
      }
    }
  }

  private class WASDEventHandler implements EventHandler<KeyEvent> {

    @Override
    public void handle(KeyEvent event) {
      KeyCode keyCode = event.getCode();

      String key = keyCode.toString();
      if ((key.equals("RIGHT") || key.equals("LEFT") || key.equals("UP") || key.equals("DOWN"))
          && !isGameOver) {
        timer.start();
        isPaused = false;
      }
      if (key.equals("RIGHT") && snake.getDirection() != Direction.LEFT) {
        snake.setDirection(Direction.RIGHT);
      } else {
        if (key.equals("LEFT") && snake.getDirection() != Direction.RIGHT) {
          snake.setDirection(Direction.LEFT);
        } else {
          if (key.equals("DOWN") && snake.getDirection() != Direction.UP) {
            snake.setDirection(Direction.DOWN);
          } else {
            if (key.equals("UP") && snake.getDirection() != Direction.DOWN) {
              snake.setDirection(Direction.UP);
            }
          }
        }
      }

      removeEventHandler(KeyEvent.KEY_PRESSED, this);
    }
  }

  private class EscEventHandler implements EventHandler<KeyEvent> {

    @Override
    public void handle(KeyEvent event) {
      KeyCode keyCode = event.getCode();
      if (keyCode == KeyCode.ESCAPE && isInGame && !isGameOver) {
        if (isPaused) {
          timer.start();
          ((AnchorPane) getRoot()).getChildren().remove(pauseLabel);
        } else {
          timer.stop();
          ((AnchorPane) getRoot()).getChildren().add(pauseLabel);
        }

        isPaused = !isPaused;
      }
    }
  }
}
