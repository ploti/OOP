package ru.nsu.fit.oop.arturploter.task_2_2_1;

import javafx.scene.paint.Color;

class Obstacle extends GameObject {

  static final Color OBSTACLE_COLOR = Color.WHITE;

  static final int OBSTACLES_START_NUMBER = 10;

  Obstacle(int x, int y) {
    super(x, y);
  }
}
