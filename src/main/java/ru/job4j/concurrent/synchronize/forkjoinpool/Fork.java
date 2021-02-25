package ru.job4j.concurrent.synchronize.forkjoinpool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class Fork extends RecursiveTask<Item> {
    private final int threads = Runtime.getRuntime().availableProcessors();
    private final ForkJoinPool pool = new ForkJoinPool(threads);
    private final int from;
    private final int to;
    private final Item[] arr;
    private int target;

    public Fork(int from, int to, Item[] arr) {
        this.from = from;
        this.to = to;
        this.arr = arr;
    }

    public Fork(int from, int to, Item[] arr, int target) {
        this.from = from;
        this.to = to;
        this.arr = arr;
        this.target = target;
    }

    private void setTarget(int target) {
        this.target = target;
    }

    public Item searchItemByIndex(int index) {
        setTarget(index);
        Item item = null;
        if (arr.length <= 10) {
            for (int i = 0; i < arr.length; i++) {
                if (arr[i].getIndex() == target) {
                    item = arr[i];
                    System.out.println(
                            String.format("SEQUENTIAL SEARCH: Item with index %s found by %s",
                                    target, Thread.currentThread().getName()));
                    break;
                }
            }
            return item;
        }
        item = pool.invoke(this);
        return item;
    }

    @Override
    protected Item compute() {
        Item item = null;
        if (from == to) {
            if (arr[from].getIndex() == target) {
                item = arr[from];
                System.out.println(
                        String.format("PARALLEL SEARCH: Item with index %s found by: %s",
                                target, Thread.currentThread().getName()));
            }
            return item;
        }
        int mid = (to + from) / 2;
        Fork firstHalf = new Fork(from, mid, arr, target);
        firstHalf.fork();
        Fork secondHalf = new Fork(mid + 1, to, arr, target);
        secondHalf.fork();
        Item first = firstHalf.join();
        Item second = secondHalf.join();
        return first != null ? first : second;
    }

}
