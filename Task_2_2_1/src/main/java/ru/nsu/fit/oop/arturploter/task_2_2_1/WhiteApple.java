package ru.nsu.fit.oop.arturploter.task_2_2_1;

import javafx.scene.paint.Color;

class WhiteApple extends GameObject {

  static final Color COLOR = Color.WHITE;

  static final int ON_BOARD_TIME = 4000;

  static final int SUPER_STATE_TIME = 9000;

  WhiteApple(double x, double y) {
    super(x, y);
  }
}
