package ru.job4j.concurrent.synchronize;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {
    @GuardedBy("this")
    private volatile Queue<T> queue = new LinkedList<>();
    private final Object monitor = this;
    private volatile int capacity;

    public SimpleBlockingQueue(int capacity) {
        this.capacity = capacity;
    }

    public void offer(T value) {
        synchronized (monitor) {
            while (queue.size() == capacity) {
                System.out.println("producer заснул");
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            queue.offer(value);
            System.out.println("добавил");
            System.out.println(queue.size());
            monitor.notifyAll();
        }
    }

    public T poll() {
        synchronized (monitor) {
            while (queue.peek() == null) {
                try {
                    System.out.println("consumer заснул");
                    monitor.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            T deleted = queue.poll();
            System.out.println("удалил");
            System.out.println(queue.size());
            monitor.notifyAll();
            return deleted;
        }
    }

}