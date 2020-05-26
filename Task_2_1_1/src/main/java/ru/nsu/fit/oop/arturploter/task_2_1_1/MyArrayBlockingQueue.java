package ru.nsu.fit.oop.arturploter.task_2_1_1;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class MyArrayBlockingQueue<E> {

  private final ReentrantLock lock;
  private final Object[] items;
  private final Condition notEmpty;
  private final Condition notFull;
  private int takeIndex;
  private int putIndex;
  private int count;

  MyArrayBlockingQueue(int capacity, boolean fair) {
    if (capacity <= 0) {
      throw new IllegalArgumentException();
    }
    this.items = new Object[capacity];
    lock = new ReentrantLock(fair);
    notEmpty = lock.newCondition();
    notFull = lock.newCondition();
  }

  private static void checkNotNull(Object v) {
    if (v == null) {
      throw new NullPointerException();
    }
  }

  @SuppressWarnings("unchecked")
  private static <E> E cast(Object item) {
    return (E) item;
  }

  void put(E e) throws InterruptedException {
    checkNotNull(e);
    final ReentrantLock lock = this.lock;
    lock.lockInterruptibly();
    try {
      while (count == items.length) {
        notFull.await();
      }
      insert(e);
    } finally {
      lock.unlock();
    }
  }

  E poll(long timeout, TimeUnit unit) throws InterruptedException {
    long nanos = unit.toNanos(timeout);
    final ReentrantLock lock = this.lock;
    lock.lockInterruptibly();
    try {
      while (count == 0) {
        if (nanos <= 0) {
          return null;
        }
        nanos = notEmpty.awaitNanos(nanos);
      }
      return extract();
    } finally {
      lock.unlock();
    }
  }

  private void insert(E x) {
    items[putIndex] = x;
    putIndex = inc(putIndex);
    ++count;
    notEmpty.signal();
  }

  private int inc(int i) {
    return (++i == items.length) ? 0 : i;
  }

  private E extract() {
    final Object[] items = this.items;
    E x = MyArrayBlockingQueue.cast(items[takeIndex]);
    items[takeIndex] = null;
    takeIndex = inc(takeIndex);
    --count;
    notFull.signal();
    return x;
  }

  int size() {
    final ReentrantLock lock = this.lock;
    lock.lock();
    try {
      return count;
    } finally {
      lock.unlock();
    }
  }
}
