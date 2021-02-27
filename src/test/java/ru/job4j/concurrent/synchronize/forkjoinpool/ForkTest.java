package ru.job4j.concurrent.synchronize.forkjoinpool;

import org.junit.Assert;
import org.junit.Test;

public class ForkTest {

    @Test
    public void whenSearchItemByIndexIsParallel() {
        Item[] arr = new Item[11];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = new Item(arr.length - i);
        }
        Fork fork = new Fork(0, 10, arr, 5);
        Item rsl = fork.searchItemByIndex();
        Assert.assertEquals(5, rsl.getIndex());
    }

    @Test
    public void whenSearchItemByIndexIsParallelIsNull() {
        Item[] arr = new Item[11];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = new Item(arr.length - i);
        }
        Fork fork = new Fork(0, 10, arr, 50);
        Item rsl = fork.searchItemByIndex();
        Assert.assertNull(rsl);
    }

    @Test
    public void whenSearchItemByIndexIsNotParallel() {
        Item[] arr = new Item[9];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = new Item(arr.length - i);
        }
        Fork fork = new Fork(0, 8, arr, 5);
        Item rsl = fork.searchItemByIndex();
        Assert.assertEquals(5, rsl.getIndex());
    }

}