package ru.job4j.concurrent.synchronize;

import junit.framework.TestCase;
import org.junit.Assert;

public class CountBarrierTest extends TestCase {
    private int firstCount = 0;
    private int secondCount = 0;

    public void testAwait() throws InterruptedException {

        CountBarrier countBarrier = new CountBarrier(10);
        Thread first = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                countBarrier.count();
                firstCount++;
                Assert.assertEquals(0, secondCount);
            }
        });
        Thread second = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                countBarrier.await();
                secondCount++;
            }
        });
        second.start();
        first.start();
        second.join();
        first.join();
        Assert.assertEquals(10, firstCount);
        Assert.assertEquals(10, secondCount);
    }
}