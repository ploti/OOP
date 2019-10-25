package com.arturploter.util;

class QueueEmptyException extends QueueUncheckedException {
    QueueEmptyException() {
        super("Queue underflow!");
    }
}
