package ru.job4j.concurrent.synchronize;

public class CountBarrier {
    private boolean flag = false;

    private final int total;

    private int count = 0;

    public CountBarrier(final int total) {
        this.total = total;
    }

    public synchronized int getCount() {
        return count;
    }

    public void on() {
        synchronized (this) {
            flag = true;
            this.notifyAll();
        }
    }

    public void off() {
        synchronized (this) {
            flag = false;
            this.notifyAll();
        }
    }

    public void check() {
        synchronized (this) {
            while (!flag) {
                try {
                    this.wait();
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
        count--;
    }

}