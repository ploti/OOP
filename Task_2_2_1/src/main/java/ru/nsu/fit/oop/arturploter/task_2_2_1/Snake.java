package ru.nsu.fit.oop.arturploter.task_2_2_1;

import java.util.ArrayList;

class Snake {

  private static final int SIZE = 2;

  private final BodyPart head;

  private final ArrayList<BodyPart> body;

  private final int headX = GameView.WIDTH / 2 + GameObject.SIZE / 2;
  private final int headY = GameView.HEIGHT / 2 + GameObject.SIZE / 2;

  private int size;

  Snake() {

    body = new ArrayList<>();
    head = new BodyPart(headX, headY);
    size = 0;
    setStart();
  }

  void setStart() {

    if (size == 0) {

      body.add(head);
      ++size;

      for (int i = 1; i < SIZE; ++i) {
        addBodyPart(headX, headY + (i * GameObject.SIZE));
      }
    } else {

      body.clear();
      head.setPosX(headX);
      head.setPosY(headY);
      size = 0;
      setStart();
    }
  }

  int getSize() {
    return this.size;
  }

  BodyPart getBodyPart(int i) {
    return body.get(i);
  }

  BodyPart getHead() {
    return this.head;
  }

  void addBodyPart(int x, int y) {
    body.add(new BodyPart(x, y));
    ++size;
  }
}
