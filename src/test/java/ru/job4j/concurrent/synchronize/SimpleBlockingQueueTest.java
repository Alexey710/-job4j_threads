package ru.job4j.concurrent.synchronize;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

import static java.lang.Thread.State.*;
import static org.hamcrest.core.Is.is;

public class SimpleBlockingQueueTest {

    @Test
    public void offer() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(1);
        Thread producer = new Thread( () -> {
            for (int i = 0; i < 2; i++) {
                queue.offer(i);
            }
        });
        Thread consumer = new Thread(() -> {
            queue.poll();
            });
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
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(1);
        Thread consumer = new Thread(() -> {
            for (int i = 0; i < 1; i++) {
                queue.poll();
            }
        }
        );
        Thread producer = new Thread(() -> {
            queue.offer(1);
            });
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

    @Test
    public void whenFetchAllThenGetIt() throws InterruptedException {
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(10);
        Thread producer = new Thread(
                () -> {
                    IntStream.range(0, 5).forEach(
                            queue::offer
                    );
                }
        );
        producer.start();
        Thread consumer = new Thread(
                () -> {
                    while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                        try {
                            buffer.add(queue.poll());
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        Assert.assertThat(buffer, is(Arrays.asList(0, 1, 2, 3, 4)));
    }

}