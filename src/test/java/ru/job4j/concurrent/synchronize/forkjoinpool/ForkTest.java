package ru.job4j.concurrent.synchronize.forkjoinpool;

import org.junit.Assert;
import org.junit.Test;
import java.util.concurrent.ForkJoinPool;

public class ForkTest {

    @Test
    public void whenSearchItemByIndexIsParallel() {
        Item[] arr = new Item[11];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = new Item(arr.length - i);
        }
        Fork fork = new Fork(0, 10, arr);
        Item rsl = fork.searchItemByIndex(5);
        Assert.assertEquals(5, rsl.getIndex());
    }

    @Test
    public void whenSearchItemByIndexIsNotParallel() {
        Item[] arr = new Item[9];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = new Item(arr.length - i);
        }
        Fork fork = new Fork(0, 8, arr);
        Item rsl = fork.searchItemByIndex(5);
        Assert.assertEquals(5, rsl.getIndex());
    }

}