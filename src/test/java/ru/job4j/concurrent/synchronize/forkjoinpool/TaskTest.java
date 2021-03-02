package ru.job4j.concurrent.synchronize.forkjoinpool;

import org.junit.Assert;
import org.junit.Test;

public class TaskTest {

    @Test
    public void whenSearchItemByIndexIsParallel() {
        Item[] arr = new Item[11];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = new Item(arr.length - i);
        }
        Item rsl = new LauncherTasks().searchItemByIndex(arr, 5);
        Assert.assertEquals(5, rsl.getIndex());
    }

    @Test
    public void whenSearchItemByIndexIsParallelIsNull() {
        Item[] arr = new Item[11];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = new Item(arr.length - i);
        }
        Item rsl = new LauncherTasks().searchItemByIndex(arr, 50);
        Assert.assertNull(rsl);
    }

    @Test
    public void whenSearchItemByIndexIsNotParallel() {
        Item[] arr = new Item[9];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = new Item(arr.length - i);
        }
        Item rsl = new LauncherTasks().searchItemByIndex(arr, 5);
        Assert.assertEquals(5, rsl.getIndex());
    }

}