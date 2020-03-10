package ru.nsu.fit.oop.arturploter.task_2_2_1;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

class ScoreView {

  static final int SCORE_HEIGHT = 50;

  private final StackPane stack;
  private final Label label2;

  ScoreView() {

    stack = new StackPane();

    stack.setStyle("-fx-background-color: " + GameView.SCENE_COLOR);
    Rectangle rectangle = new Rectangle(GameView.WIDTH - (2 * SCORE_HEIGHT), SCORE_HEIGHT);

    rectangle.setFill(Color.WHITE);

    Label label1 = new Label("SCORE: ");
    label1.setFont(new Font("Segoe UI Bold", 24));

    Color scoreColor = Color.rgb(22, 22, 22);
    label1.setTextFill(scoreColor);

    label2 = new Label("0");
    label2.setFont(new Font("Segoe UI Bold", 24));
    label2.setTextFill(scoreColor);

    stack.getChildren().addAll(rectangle, label1, label2);
    stack.getChildren().get(1).setTranslateX(-30);
    stack.getChildren().get(2).setTranslateX(40);
  }

  void addScore(int score) {
    label2.setText(Integer.toString(score));
  }

  StackPane getStack() {
    return stack;
  }
}
