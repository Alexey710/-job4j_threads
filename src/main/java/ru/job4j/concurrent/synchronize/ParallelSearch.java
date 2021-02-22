package ru.job4j.concurrent.synchronize;

public class ParallelSearch {
    public static void test(int amount) {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(10);
        final Thread consumer = new Thread(
                () -> {
                    while (!Thread.currentThread().isInterrupted()) {
                        try {
                            Thread.sleep(500);
                            System.out.println(queue.poll());
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                    System.out.println("consumer закончил работу");
                }
        );
        consumer.start();
        new Thread(
                () -> {
                    for (int index = 0; index != amount; index++) {
                        queue.offer(index);
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    consumer.interrupt();
                    System.out.println("producer закончил работу");
                }
        ).start();
    }

    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            test(3);
        }
    }
}