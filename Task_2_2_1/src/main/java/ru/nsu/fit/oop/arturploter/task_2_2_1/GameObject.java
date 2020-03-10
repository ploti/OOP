package ru.nsu.fit.oop.arturploter.task_2_2_1;

abstract class GameObject {

  static final int SIZE = 20;
  private int posX;
  private int posY;

  GameObject(int posX, int posY) {

    this.posX = posX;
    this.posY = posY;
  }

  int getPosX() {
    return posX;
  }

  void setPosX(int posX) {
    this.posX = posX;
  }

  int getPosY() {
    return posY;
  }

  void setPosY(int posY) {
    this.posY = posY;
  }
}
