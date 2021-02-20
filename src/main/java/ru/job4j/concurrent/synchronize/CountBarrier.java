package ru.job4j.concurrent.synchronize;

public class CountBarrier {
    private final Object monitor = this;
    private volatile boolean flag = false;

    private final int total;

    private volatile int count = 0;

    public CountBarrier(final int total) {
        this.total = total;
    }

    public void on() {
        synchronized (monitor) {
            flag = true;
            monitor.notifyAll();
        }
    }

    public void off() {
        synchronized (monitor) {
            flag = false;
            monitor.notifyAll();
        }
    }

    public void check() {
        synchronized (monitor) {
            while (!flag) {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public synchronized void count() {
        count++;
        if (count == total) {
            on();
        }
    }

    public synchronized void await() {
        check();
    }

}