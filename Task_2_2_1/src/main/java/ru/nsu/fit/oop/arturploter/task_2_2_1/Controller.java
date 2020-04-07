package ru.nsu.fit.oop.arturploter.task_2_2_1;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

class Controller {

  private static State state;

  private final Snake snake;
  private final BodyPart head;
  private final GameView view;
  private final Playfield playfield;

  private boolean up;

  private boolean down;
  private boolean right;
  private boolean left;
  private boolean pause;
  private boolean resume;
  private boolean start;

  private boolean keyActive;

  private int dx;

  private int dy;

  private int speedConstraint;

  private int speedPointsConstraint;

  Controller() {

    state = State.Started;
    up = false;
    down = false;
    right = false;
    left = false;
    pause = false;
    resume = false;
    start = false;
    view = new GameView(new AnchorPane());
    snake = view.getSnake();
    head = snake.getHead();
    playfield = view.getPlayfield();
    keyActive = true;

    resume();
  }

  static State getState() {
    return state;
  }

  private void movement(Scene scene) {

    scene.setOnKeyPressed(
        e -> {
          if (e.getCode() == KeyCode.UP || e.getCode() == KeyCode.W) {
            if (!down && keyActive && state == State.Running) {
              up = true;
              left = false;
              right = false;
              keyActive = false;
            }
          } else if (e.getCode() == KeyCode.DOWN || e.getCode() == KeyCode.S) {
            if (!up && keyActive && (left || right) && state == State.Running) {
              down = true;
              left = false;
              right = false;
              keyActive = false;
            }
          } else if (e.getCode() == KeyCode.LEFT || e.getCode() == KeyCode.A) {
            if (!right && keyActive && state == State.Running) {
              left = true;
              up = false;
              down = false;
              keyActive = false;
            }
          } else if (e.getCode() == KeyCode.RIGHT || e.getCode() == KeyCode.D) {
            if (!left && keyActive && state == State.Running) {
              right = true;
              up = false;
              down = false;
              keyActive = false;
            }
          } else if (e.getCode() == KeyCode.SPACE) {
            if (state == State.Running || state == State.Paused) {
              if (!pause) {
                pause = true;
                resume = false;
              } else {
                resume = true;
                pause = false;
                resume();
              }
            }
          } else if (e.getCode() == KeyCode.ENTER) {
            if (state == State.Started) {
              start = true;
            }
            if (state == State.Finished) {
              start = true;
              resume();
            }
          } else if (e.getCode() == KeyCode.ESCAPE) {
            System.exit(0);
          }
        });

    scene.setOnKeyReleased(event -> {});
  }

  private void move(int dx, int dy) {

    if (dx != 0 || dy != 0) {

      BodyPart prev = new BodyPart(head.getPosX(), head.getPosY());
      BodyPart next = new BodyPart(head.getPosX(), head.getPosY());

      head.setPosX(head.getPosX() + (dx * GameObject.SIZE));

      if (head.getPosX() > GameView.WIDTH) {
        head.setPosX(GameObject.SIZE / 2);
      } else if (head.getPosX() < 0) {
        head.setPosX(GameView.WIDTH - GameObject.SIZE / 2);
      }

      head.setPosY(head.getPosY() + (dy * GameObject.SIZE));

      if (head.getPosY() > GameView.HEIGHT) {

        if ((head.getPosX() == GameObject.SIZE / 2
                || head.getPosX() == GameView.HEIGHT - GameObject.SIZE / 2)
            && head.getPosY() == GameView.HEIGHT + GameObject.SIZE / 2) ;
        else head.setPosY(GameObject.SIZE / 2);
      } else if (head.getPosY() < 0) {
        head.setPosY(GameView.HEIGHT - GameObject.SIZE / 2);
      }

      for (int i = 1; i < snake.getSize(); ++i) {

        next.setPosX(snake.getBodyPart(i).getPosX());
        next.setPosY(snake.getBodyPart(i).getPosY());

        snake.getBodyPart(i).setPosX(prev.getPosX());
        snake.getBodyPart(i).setPosY(prev.getPosY());
        prev.setPosX(next.getPosX());
        prev.setPosY(next.getPosY());
      }
    }
  }

  private void resume() {

    new AnimationTimer() {

      int i = 0;

      @Override
      public void handle(long now) {

        if (up && !down) {

          dy = -1;
          dx = 0;
        }

        if (!up && down) {

          dy = 1;
          dx = 0;
        }

        if (left && !right) {

          dy = 0;
          dx = -1;
        }

        if (right && !left) {
          dy = 0;
          dx = 1;
        }

        if (pause && !resume) {
          state = State.Paused;
          view.render();
          stop();
        }

        if (resume && !pause) {
          state = State.Running;
          resume = false;
        }

        if (start && (state == State.Started || state == State.Finished)) {
          restart();
          start = false;
        }

        if (state == State.Finished) {
          stop();
        }

        if (state == State.Running) {
          if (i == speedConstraint) {
            move(dx, dy);
            keyActive = true;
            i = 0;
          }
          ++i;
        }

        update();
        view.render();
        movement(view.getScene());
      }
    }.start();
  }

  private void update() {

    playfield.updateFruit();
    playfield.updateObstacles();
    playfield.checkEaten();
    playfield.updateScore();
    if (playfield.checkCollision() == State.Finished) {
      state = State.Finished;
    }

    if (speedConstraint > 2 && playfield.getScore() >= speedPointsConstraint) speedConstraint = 2;
    if ((speedConstraint == 2) && (playfield.getScore() - speedPointsConstraint) >= 10) {
      speedPointsConstraint += 30;
      speedConstraint = 3;
    }
  }

  private void restart() {
    state = State.Running;
    dx = dy = 0;
    up = down = left = right = false;
    speedConstraint = 3;
    speedPointsConstraint = 50;
  }

  Stage getStage() {
    return view.getStage();
  }
}
