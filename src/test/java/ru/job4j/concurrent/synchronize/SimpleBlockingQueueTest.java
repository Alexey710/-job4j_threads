package ru.job4j.concurrent.synchronize;

import org.junit.Assert;
import org.junit.Test;
import static java.lang.Thread.State.*;

public class SimpleBlockingQueueTest {

    @Test
    public void offer() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>();
        Thread producer = new Thread( () -> {
            for (int i = 0; i < 2; i++) {
                queue.offer(i);
            }
        });
        Thread consumer = new Thread(() -> queue.poll());
        Thread auditor = new Thread(() ->  {
            while (producer.getState() == RUNNABLE) {
            }
            if (producer.getState() == WAITING) {
                consumer.start();
            };
        });
        producer.start();
        auditor.start();
        producer.join();
        auditor.join();
        consumer.join();
        Assert.assertTrue(producer.getState() == TERMINATED);

    }

    @Test
    public void poll() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>();
        Thread consumer = new Thread(queue::poll);
        Thread producer = new Thread(() -> queue.offer(1));
        Thread auditor = new Thread(() ->  {
            while (consumer.getState() == RUNNABLE) {
            }
            if (consumer.getState() == WAITING) {
                producer.start();
            }
        });
        consumer.start();
        auditor.start();
        consumer.join();
        auditor.join();
        producer.join();
        Assert.assertTrue(consumer.getState() == TERMINATED);
    }

}