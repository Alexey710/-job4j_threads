package ru.job4j.concurrent.synchronize;

import net.jcip.annotations.ThreadSafe;
import java.util.LinkedList;
import java.util.List;

@ThreadSafe
public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(100);

    public ThreadPool() {
        int size = Runtime.getRuntime().availableProcessors();
        for (int i = 0; i < size; i++) {
            Thread thread = new PoolWorker();
            threads.add(thread);
            thread.start();
        }
    }

    public void work(Runnable job) {
        tasks.offer(job);
    }

    public void shutdown() {
        for (Thread thread : threads) {
            thread.interrupt();
        }
    }

    public class PoolWorker extends Thread {
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                tasks.poll().run();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        List<Runnable> list = new LinkedList<>();
        for (int i = 0; i < 2000; i++) {
            Runnable task = () -> {
                String threadName = Thread.currentThread().getName();
                System.out.println("Executed by " + threadName);
            };
            list.add(task);
        }
        ThreadPool threadPool = new ThreadPool();
        for (Runnable task : list) {
            threadPool.work(task);
        }
        threadPool.shutdown();
    }

}