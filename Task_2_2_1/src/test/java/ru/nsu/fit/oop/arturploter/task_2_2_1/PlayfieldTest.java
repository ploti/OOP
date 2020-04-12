package ru.nsu.fit.oop.arturploter.task_2_2_1;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

class PlayfieldTest extends ApplicationTest {
  private Playfield playfield;

  @Override
  public void start(Stage stage) {
    AnchorPane anchorPane = new AnchorPane();
    anchorPane.setId("desktop");

    playfield = new Playfield(anchorPane);
    stage.setScene(playfield);
    stage.show();
  }

  @Test
  void snakeCollidesWithItself_GameShouldBeOver() {
    press(KeyCode.RIGHT);

    Snake snake = playfield.getSnake();
    IntStream.range(1, 30).forEach(i -> snake.addSegment());

    sleep(100);
    press(KeyCode.DOWN).press(KeyCode.LEFT).press(KeyCode.UP);
    sleep(1, TimeUnit.SECONDS);

    assertTrue(playfield.isGameOver());
  }

  @Test
  void snakeRunsOutOfTheField_GameShouldBeOver() {
    press(KeyCode.DOWN);
    sleep(500);

    press(KeyCode.DOWN);
    sleep(500);

    assertTrue(playfield.isGameOver());
  }
}
