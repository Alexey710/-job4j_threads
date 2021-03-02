package ru.job4j.concurrent.synchronize.forkjoinpool;

import java.util.concurrent.ForkJoinPool;

public class LauncherTasks {
    private final int threads = Runtime.getRuntime().availableProcessors();
    private final ForkJoinPool pool = new ForkJoinPool(threads);

    public Item searchItemByIndex(Item[] arr, int target) {
        return pool.invoke(new Task(0, arr.length - 1, arr, target));
    }
}
