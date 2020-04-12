package ru.nsu.fit.oop.arturploter.task_2_2_1;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * The class represents the controller of the {@code MainMenuView.fxml} view.
 */
public class MainMenuController {
  private static final int DIFFICULTY = 100_000_000;

  @FXML private Button startButton;

  /**
   * Exits from the application.
   */
  public void exit() {
    System.exit(0);
  }

  /**
   * Starts the game.
   */
  public void startButtonAction() {
    AnchorPane root = new AnchorPane();

    Stage parentView = (Stage) startButton.getScene().getWindow();
    parentView.setScene(new Playfield(root, DIFFICULTY));
    parentView.centerOnScreen();
    parentView.show();
  }
}
