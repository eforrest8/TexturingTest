package com.sgolc.utils;

import java.util.Queue;
import java.util.concurrent.*;

public class BlockingWrapper<T> implements AutoCloseable {

    private final T backingObject;

    private final Queue<Operation> waitingOperations = new ConcurrentLinkedQueue<>();

    public BlockingWrapper(T obj) {
        backingObject = obj;
    }

    public T open(boolean readOnly) throws InterruptedException {
        boolean onlyOperation = waitingOperations.isEmpty();
        waitingOperations.offer(new Operation(Thread.currentThread(), readOnly));
        return onlyOperation ? backingObject : parkThreadUntilReady();
    }

    private T parkThreadUntilReady() throws InterruptedException {
        Thread.currentThread().wait();
        return backingObject;
    }

    @Override
    public void close() {
        Operation next = waitingOperations.peek();
        if (next == null) {
            return;
        }
        if (next.readOnly()) {
            while (next != null && next.readOnly()) {
                next.thread().notify();
                next = waitingOperations.poll();
            }
        } else {
            next.thread().notify();
        }
    }



    private record Operation(Thread thread, boolean readOnly) {
    }
}
