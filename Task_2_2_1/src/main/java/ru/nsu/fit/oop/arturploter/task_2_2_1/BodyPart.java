package ru.nsu.fit.oop.arturploter.task_2_2_1;

import javafx.scene.paint.Color;

class BodyPart extends GameObject {

  static final Color BODY_COLOR = Color.rgb(110, 145, 110);

  static final Color SUPER_BODY_COLOR = Color.WHITE;

  static final Color HEAD_COLOR = Color.rgb(110, 145, 110);

  BodyPart(int x, int y) {
    super(x, y);
  }
}
