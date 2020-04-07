package ru.nsu.fit.oop.arturploter.task_2_2_1;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GameView extends Scene {

  static final double WIDTH = 800.0d;

  static final double HEIGHT = 600.0d;

  static final String SCENE_COLOR = "#161616";

//  private final Scene scene;

//  private final Stage stage;

  private final Snake snake;

  private final Playfield playfield;

  private final List<Apple> apples;

  private final List<Obstacle> obstacles;
  private final Canvas canvas;
//  private final GridPane grid;
  private GraphicsContext graphicsContext;
  private WhiteApple whiteApple;

  private Group group;
  private StackPane stack;

  private Label pauseLabel;
  private Label gameOverLabel;
  private Label scoreLabel;
  private Label inGameScoreLabel;

  public GameView(Parent root) {
    super(root);

    playfield = new Playfield();
    snake = playfield.getSnake();
    apples = playfield.getApples();
    whiteApple = playfield.getWhiteApple();
    obstacles = playfield.getObstacles();

//    stage = new Stage();
//    stage.setTitle("Snake");

    canvas = new Canvas(WIDTH, HEIGHT);
    ((Pane) root).getChildren().add(canvas);

    graphicsContext = canvas.getGraphicsContext2D();

    //    canvas = new Pane();
    //    canvas.setStyle("-fx-background-color: " + SCENE_COLOR);
    //    canvas.setPrefSize(WIDTH, HEIGHT);

//    stack = new StackPane();
//    grid = new GridPane();
//
//    group = new Group();
//    scene = new Scene(group, WIDTH, HEIGHT + ScoreView.SCORE_HEIGHT);
//    scene.setFill(Color.web(SCENE_COLOR));

    initLabels();
    initScreen();
//    render();
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
//        whenFinished();
        renderGameOverMessage();
        break;
    }
  }

  private void initLabels() {
    pauseLabel = new Label("Paused");
    pauseLabel.setLayoutX(WIDTH / 2f - 25);
    pauseLabel.setLayoutY(HEIGHT / 2f);
    pauseLabel
        .getStylesheets()
        .add(
            Objects.requireNonNull(
                    getClass().getClassLoader().getResource("styles/GeneralStyle.css"))
                .toString());

    gameOverLabel = new Label("Game Over");
    gameOverLabel.setLayoutX(WIDTH / 2f - 75);
    gameOverLabel.setLayoutY(HEIGHT / 2f - 40);
    gameOverLabel
        .getStylesheets()
        .add(
            Objects.requireNonNull(
                    getClass().getClassLoader().getResource("styles/GeneralStyle.css"))
                .toString());

    scoreLabel = new Label();
    scoreLabel.setLayoutX(WIDTH / 2f - 125);
    scoreLabel.setLayoutY(HEIGHT / 2f - 10);
    scoreLabel
        .getStylesheets()
        .add(
            Objects.requireNonNull(
                    getClass().getClassLoader().getResource("styles/GeneralStyle.css"))
                .toString());

    inGameScoreLabel = new Label();
    inGameScoreLabel.setId("inGameScoreLabel");
    inGameScoreLabel.setLayoutX(0);
    inGameScoreLabel.setLayoutY(0);
    inGameScoreLabel
        .getStylesheets()
        .add(
            Objects.requireNonNull(
                    getClass().getClassLoader().getResource("styles/GeneralStyle.css"))
                .toString());

    ((AnchorPane) getRoot()).getChildren().add(inGameScoreLabel);
  }

  private void initScreen() {
    inGameScoreLabel.setText("Score: " + playfield.getScoreView() + "pt.");

    renderBackground();
    render();
  }

  private void renderBackground() {
    graphicsContext.setFill(Color.BLACK);
    graphicsContext.fillRect(0, 0, WIDTH, HEIGHT);
    renderGrid(graphicsContext);
  }

  private void renderGrid(GraphicsContext graphicsContext) {
    graphicsContext.setStroke(Color.GRAY);
    for (int i = 0; i < WIDTH; i += GameObject.SIZE) {
      graphicsContext.strokeLine(i, 0, i, HEIGHT);
    }
    for (int i = 0; i < HEIGHT; i += GameObject.SIZE) {
      graphicsContext.strokeLine(0, i, WIDTH, i);
    }
  }

  private void renderGameOverMessage() {
    scoreLabel.setText("Your score: " + playfield.getFinalScore());

    Button restartButton = new Button("Restart");
    restartButton.setLayoutX(WIDTH / 2f - 125);
    restartButton.setLayoutY(HEIGHT / 2f + 50);
    restartButton
        .getStylesheets()
        .add(
            Objects.requireNonNull(
                    getClass().getClassLoader().getResource("styles/GameOverStyle.css"))
                .toString());

    Button exitButton = new Button("Exit");
    exitButton.setLayoutX(WIDTH / 2f - 50);
    exitButton.setLayoutY(HEIGHT / 2f + 100);
    exitButton
        .getStylesheets()
        .add(
            Objects.requireNonNull(
                    getClass().getClassLoader().getResource("styles/GameOverStyle.css"))
                .toString());

    Button backButton = new Button("Back");
    backButton.setLayoutX(WIDTH / 2f + 30);
    backButton.setLayoutY(HEIGHT / 2f + 50);
    backButton
        .getStylesheets()
        .add(
            Objects.requireNonNull(
                    getClass().getClassLoader().getResource("styles/GameOverStyle.css"))
                .toString());

    exitButton.setOnMouseClicked(e -> System.exit(0));

    restartButton.setOnMouseClicked(
        e -> {
//          gameOver = false;
          ((AnchorPane) getRoot())
              .getChildren()
              .removeAll(gameOverLabel, scoreLabel, restartButton, exitButton, backButton);

          initScreen();
        });

    backButton.setOnMouseClicked(
        e -> {
          Stage stage = (Stage) getWindow();
          Parent root = null;

          try {
            root =
                FXMLLoader.load(
                    Objects.requireNonNull(
                        getClass().getClassLoader().getResource("views/MainMenuViewStyle.fxml")));
          } catch (IOException ex) {
            System.out.println("MainMenuViewStyle not found.");
            ex.printStackTrace();
          }

          stage.setScene(new Scene(Objects.requireNonNull(root)));
          stage.centerOnScreen();
          stage.show();
        });

    ((AnchorPane) getRoot())
        .getChildren()
        .addAll(gameOverLabel, scoreLabel, restartButton, exitButton, backButton);
  }

  Scene getScene() {
//    return stage.getScene();
    return getWindow().getScene();
  }

  Stage getStage() {
//    return stage;
    return (Stage) getWindow();
  }

  private void whenStarted() {
    Stage stage = (Stage) getWindow();
    Parent root = null;

    try {
      root =
          FXMLLoader.load(
              Objects.requireNonNull(getClass().getClassLoader()
                  .getResource("views/MainMenuView.fxml")));
    } catch (IOException ex) {
      System.out.println("MainMenuViewStyle not found.");
      ex.printStackTrace();
    }

    Scene scene = new Scene(Objects.requireNonNull(root));

    if (root == null)
    {
      System.out.println("root is null");
    }

    if (scene == null)
    {
      System.out.println("scene is null");
    }

    stage.setScene(new Scene(Objects.requireNonNull(root)));
    stage.centerOnScreen();
    stage.show();

    // TODO ???
//    ((AnchorPane) getRoot())
//      .getChildren()
//        .addAll(gameOverLabel, scoreLabel);

//    group = new Group();
//
//    Text title = new Text(WIDTH / 2d - 120, HEIGHT / 2d - 5, "Snake");
//    title.setFont(Font.font("Segoe UI Bold", 72));
//    title.setFill(Color.WHITE);
//
//    Text pressSpace = new Text(WIDTH / 2d - 120, HEIGHT / 2d + 35, "Press SPACE to play");
//    pressSpace.setFont(Font.font("Segoe UI", 18));
//    pressSpace.setFill(Color.rgb(168, 167, 167));
//
//    Text pressEsc = new Text(WIDTH / 2d - 120, HEIGHT / 2d + 60, "Press ESC to pause");
//    pressEsc.setFont(Font.font("Segoe UI", 18));
//    pressEsc.setFill(Color.rgb(168, 167, 167));
//
//    group.getChildren().addAll(pressEsc, pressSpace, title);
//
//    scene.setRoot(group);
//    stage.setScene(scene);
  }

  private void whenRunning() {

//    grid.getChildren().clear();
//    canvas.getChildren().clear();

    stack = playfield.getScoreView().getStack();
    whiteApple = playfield.getWhiteApple();

    double helpX;
    double helpY;
    double snakeY;
    double snakeX;

//    Circle circle =
//        new Circle(snake.getHead().getPosX(), snake.getHead().getPosY(), GameObject.SIZE / 2d);
//    circle.setFill(BodyPart.HEAD_COLOR);

    graphicsContext.setFill(BodyPart.HEAD_COLOR);
    graphicsContext.fillOval(snake.getHead().getPosX(), snake.getHead().getPosY(), GameObject.SIZE, GameObject.SIZE);
//    canvas.getChildren().add(circle);

    Color bodyColor;

    if (playfield.getGodMode()) {
      bodyColor = BodyPart.SUPER_BODY_COLOR;
    } else {
      bodyColor = BodyPart.BODY_COLOR;
    }

    for (int i = 1; i < snake.getSize(); ++i) {
      snakeX = snake.getBodyPart(i).getPosX();
      snakeY = snake.getBodyPart(i).getPosY();
//      circle = new Circle(snakeX, snakeY, GameObject.SIZE / 2d);
//      circle.setFill(bodyColor);
//      canvas.getChildren().add(circle);
      graphicsContext.setFill(bodyColor);
      graphicsContext.fillOval(snakeX, snakeY, GameObject.SIZE, GameObject.SIZE);
    }

    for (Apple apple : apples) {
      helpX = apple.getPosX();
      helpY = apple.getPosY();
//      circle = new Circle(helpX, helpY, GameObject.SIZE / 2d);
//      circle.setFill(Apple.FRUIT_COLOR);
//      canvas.getChildren().add(circle);

      graphicsContext.setFill(Apple.FRUIT_COLOR);
      graphicsContext.fillOval(helpX, helpY, GameObject.SIZE, GameObject.SIZE);
    }

    if (whiteApple != null) {
//      circle = new Circle(whiteApple.getPosX(), whiteApple.getPosY(), GameObject.SIZE / 2d);
//      circle.setFill(WhiteApple.COLOR);
//      canvas.getChildren().add(circle);

      graphicsContext.setFill(WhiteApple.COLOR);
      graphicsContext.fillOval(whiteApple.getPosX(), whiteApple.getPosY(), GameObject.SIZE, GameObject.SIZE);
    }

    for (Obstacle obstacle : obstacles) {
      helpX = obstacle.getPosX();
      helpY = obstacle.getPosY();
//      Rectangle rectangle =
//          new Rectangle(
//              helpX - (GameObject.SIZE / 2d),
//              helpY - (GameObject.SIZE / 2d),
//              GameObject.SIZE,
//              GameObject.SIZE);
//
//      rectangle.setFill(Obstacle.OBSTACLE_COLOR);
//      canvas.getChildren().add(rectangle);

      graphicsContext.setFill(Obstacle.OBSTACLE_COLOR);
      graphicsContext.fillRect(helpX - (GameObject.SIZE / 2d), helpY - (GameObject.SIZE / 2d), GameObject.SIZE, GameObject.SIZE);
    }
//
//    grid.add(stack, 0, 1);
//    grid.add(canvas, 0, 0);
//
//    scene.setRoot(grid);
//    stage.setScene(scene);
  }

  private void whenPaused() {
    group = new Group();

    Text gamePaused = new Text(WIDTH / 2d - 150, HEIGHT / 2d + 20, "Game Paused");
    gamePaused.setFont(Font.font("Segoe UI Bold", 48));
    gamePaused.setFill(Color.WHITE);

    group.getChildren().add(gamePaused);
//    scene.setRoot(group);
//    stage.setScene(scene);
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
//    scene.setRoot(group);
//    stage.setScene(scene);
  }

  Snake getSnake() {
    return snake;
  }

  Playfield getPlayfield() {
    return playfield;
  }
}
