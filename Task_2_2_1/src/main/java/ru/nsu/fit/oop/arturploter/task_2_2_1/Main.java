package ru.nsu.fit.oop.arturploter.task_2_2_1;

import javafx.application.Application;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {
  private AnchorPane anchorPane;
  private GameView gameView;

  public static void main(String[] args) {
    launch(args);
  }

    @Override
    public void start(Stage stage) {

      Controller setUpGame = new Controller();

      stage = setUpGame.getStage();
      stage.setResizable(false);
      stage.show();
    }

//  @Override
//  public void start(Stage stage) {
//    anchorPane = new AnchorPane();
//    anchorPane.setId("desktop");
//    gameView = new GameView(anchorPane);
//    stage.setScene(gameView);
//    stage.show();
//  }
}
