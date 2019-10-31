package ru.nsu.fit.oop.arturploter.task_2_1;

class StackUnderflowException extends StackUncheckedException {
    StackUnderflowException() {
        super("Stack Underflow");
    }
}