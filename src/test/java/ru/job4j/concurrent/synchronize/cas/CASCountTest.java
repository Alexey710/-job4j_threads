package ru.job4j.concurrent.synchronize.cas;

import org.junit.Assert;
import org.junit.Test;

public class CASCountTest {

    @Test
    public void increment() throws InterruptedException {
        CASCount cas = new CASCount();
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                cas.increment();
                System.out.println(Thread.currentThread().getName());
            }
        });
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                cas.increment();
                System.out.println(Thread.currentThread().getName());
            }
        });
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        Assert.assertEquals(2000, cas.get());
    }
}