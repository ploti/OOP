package ru.nsu.fit.oop.arturploter.task_2_2_1;

import java.util.List;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

class GameView {

  static final int WIDTH = 800;

  static final int HEIGHT = 600;

  static final String SCENE_COLOR = "#161616";

  private final Scene scene;

  private final Stage stage;

  private final Snake snake;

  private final Playfield playfield;

  private final List<Apple> apples;

  private final List<Obstacle> obstacles;

  private final Pane canvas;
  private final GridPane grid;

  private WhiteApple whiteApple;

  private Group group;
  private StackPane stack;

  GameView() {

    playfield = new Playfield();
    snake = playfield.getSnake();
    apples = playfield.getApples();
    whiteApple = playfield.getWhiteApple();
    obstacles = playfield.getObstacles();

    stage = new Stage();
    stage.setTitle("Snake");

    canvas = new Pane();
    canvas.setStyle("-fx-background-color: " + SCENE_COLOR);
    canvas.setPrefSize(WIDTH, HEIGHT);

    stack = new StackPane();
    grid = new GridPane();

    group = new Group();
    scene = new Scene(group, WIDTH, HEIGHT + ScoreView.SCORE_HEIGHT);
    scene.setFill(Color.web(SCENE_COLOR));

    render();
  }

  void render() {

    State state = Controller.getState();
    switch (state) {
      case Started:
        whenStarted();
        break;
      case Running:
        whenRunning();
        break;
      case Paused:
        whenPaused();
        break;
      default:
        whenFinished();
        break;
    }
  }

  Scene getScene() {
    return stage.getScene();
  }

  Stage getStage() {
    return stage;
  }

  private void whenStarted() {

    group = new Group();

    Text title = new Text(WIDTH / 2d - 120, HEIGHT / 2d - 5, "Snake");
    title.setFont(Font.font("Segoe UI Bold", 72));
    title.setFill(Color.WHITE);

    Text pressSpace = new Text(WIDTH / 2d - 120, HEIGHT / 2d + 35, "Press SPACE to play");
    pressSpace.setFont(Font.font("Segoe UI", 18));
    pressSpace.setFill(Color.rgb(168, 167, 167));

    Text pressEsc = new Text(WIDTH / 2d - 120, HEIGHT / 2d + 60, "Press ESC to pause");
    pressEsc.setFont(Font.font("Segoe UI", 18));
    pressEsc.setFill(Color.rgb(168, 167, 167));

    group.getChildren().addAll(pressEsc, pressSpace, title);

    scene.setRoot(group);
    stage.setScene(scene);
  }

  private void whenRunning() {

    grid.getChildren().clear();
    canvas.getChildren().clear();
    stack = playfield.getScoreView().getStack();
    whiteApple = playfield.getWhiteApple();

    int helpX;
    int helpY;
    int snakeY;
    int snakeX;

    Circle circle =
        new Circle(snake.getHead().getPosX(), snake.getHead().getPosY(), GameObject.SIZE / 2d);
    circle.setFill(BodyPart.HEAD_COLOR);

    canvas.getChildren().add(circle);

    Color bodyColor;

    if (playfield.getGodMode()) {
      bodyColor = BodyPart.SUPER_BODY_COLOR;
    } else {
      bodyColor = BodyPart.BODY_COLOR;
    }

    for (int i = 1; i < snake.getSize(); ++i) {
      snakeX = snake.getBodyPart(i).getPosX();
      snakeY = snake.getBodyPart(i).getPosY();
      circle = new Circle(snakeX, snakeY, GameObject.SIZE / 2d);
      circle.setFill(bodyColor);
      canvas.getChildren().add(circle);
    }

    for (Apple apple : apples) {
      helpX = apple.getPosX();
      helpY = apple.getPosY();
      circle = new Circle(helpX, helpY, GameObject.SIZE / 2d);
      circle.setFill(Apple.FRUIT_COLOR);
      canvas.getChildren().add(circle);
    }

    if (whiteApple != null) {
      circle = new Circle(whiteApple.getPosX(), whiteApple.getPosY(), GameObject.SIZE / 2d);
      circle.setFill(WhiteApple.COLOR);
      canvas.getChildren().add(circle);
    }

    for (Obstacle obstacle : obstacles) {
      helpX = obstacle.getPosX();
      helpY = obstacle.getPosY();
      Rectangle r =
          new Rectangle(
              helpX - (GameObject.SIZE / 2d),
              helpY - (GameObject.SIZE / 2d),
              GameObject.SIZE,
              GameObject.SIZE);

      r.setFill(Obstacle.OBSTACLE_COLOR);
      canvas.getChildren().add(r);
    }

    grid.add(stack, 0, 1);
    grid.add(canvas, 0, 0);

    scene.setRoot(grid);
    stage.setScene(scene);
  }

  private void whenPaused() {

    group = new Group();

    Text gamePaused = new Text(WIDTH / 2d - 150, HEIGHT / 2d + 20, "Game Paused");
    gamePaused.setFont(Font.font("Segoe UI Bold", 48));
    gamePaused.setFill(Color.WHITE);

    group.getChildren().add(gamePaused);
    scene.setRoot(group);
    stage.setScene(scene);
  }

  private void whenFinished() {

    group = new Group();

    Text finalScore =
        new Text(WIDTH / 2d - 150, HEIGHT / 2d + 10, "Final Score: " + playfield.getFinalScore());
    finalScore.setFont(Font.font("Segoe UI Bold", 48));
    finalScore.setFill(Color.WHITE);

    Text pressEnter = new Text(WIDTH / 2d - 150, HEIGHT / 2d + 45, "Press ENTER to replay");
    pressEnter.setFont(Font.font("Segoe UI", 18));
    pressEnter.setFill(Color.rgb(167, 166, 166));

    group.getChildren().addAll(pressEnter, finalScore);
    scene.setRoot(group);
    stage.setScene(scene);
  }

  Snake getSnake() {
    return snake;
  }

  Playfield getPlayfield() {
    return playfield;
  }
}
