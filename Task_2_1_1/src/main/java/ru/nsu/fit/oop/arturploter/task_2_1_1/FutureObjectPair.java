package ru.nsu.fit.oop.arturploter.task_2_1_1;

import java.util.concurrent.Future;

class FutureObjectPair {
  final Object object;
  final Future<?> future;

  FutureObjectPair(Object object, Future<?> future) {
    this.object = object;
    this.future = future;
  }
}
