package ru.job4j.concurrent.synchronize;

import org.junit.Assert;
import org.junit.Test;


public class CountBarrierTest {
    @Test
    public void testAwait() throws InterruptedException {
        CountBarrier countBarrier = new CountBarrier(10);
        Thread first = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                countBarrier.count();
            }
        });
        Thread second = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                countBarrier.await();
            }
        });
        second.start();
        first.start();
        second.join();
        first.join();
        Assert.assertEquals(0, countBarrier.getCount());
    }
}