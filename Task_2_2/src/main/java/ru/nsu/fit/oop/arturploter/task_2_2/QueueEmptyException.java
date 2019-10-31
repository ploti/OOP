package ru.nsu.fit.oop.arturploter.task_2_2;

class QueueEmptyException extends QueueUncheckedException {
    QueueEmptyException() {
        super("Queue underflow!");
    }
}
