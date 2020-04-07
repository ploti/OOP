package ru.nsu.fit.oop.arturploter.task_2_2_1;

abstract class GameObject {

  static final double SIZE = 20;
  private double posX;
  private double posY;

  GameObject(double posX, double posY) {

    this.posX = posX;
    this.posY = posY;
  }

  double getPosX() {
    return posX;
  }

  void setPosX(double posX) {
    this.posX = posX;
  }

  double getPosY() {
    return posY;
  }

  void setPosY(double posY) {
    this.posY = posY;
  }
}
