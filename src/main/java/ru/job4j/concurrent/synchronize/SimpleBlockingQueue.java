package ru.job4j.concurrent.synchronize;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {
    private final Object monitor = this;
    private boolean isEmpty = true;

    @GuardedBy("this")
    private volatile Queue<T> queue = new LinkedList<>();

    public void offer(T value) {
        synchronized (monitor) {
            if (queue.peek() != null) {
                isEmpty = false;
            }
            while (!isEmpty) {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            queue.offer(value);
            isEmpty = false;
            monitor.notifyAll();
        }
    }

    public T poll() {
        synchronized (monitor) {
            if (queue.peek() != null) {
                isEmpty = false;
            }
            while (isEmpty) {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            T deleted = queue.poll();
            isEmpty = true;
            monitor.notifyAll();
            return deleted;
        }
    }

}