package ru.job4j.concurrent.synchronize.forkjoinpool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class Fork extends RecursiveTask<Item> {
    private final int threads = Runtime.getRuntime().availableProcessors();
    private final ForkJoinPool pool = new ForkJoinPool(threads);
    private final int from;
    private final int to;
    private final Item[] arr;
    private final int target;

    public Fork(int from, int to, Item[] arr, int target) {
        this.from = from;
        this.to = to;
        this.arr = arr;
        this.target = target;
    }

    private Item processing(String typeSearch, int start, int end) {
        Item item = null;
        for (int i = start; i <= end; i++) {
            if (arr[i].getIndex() == target) {
                item = arr[i];
                System.out.println(
                        String.format("%s: Item with index %s found by %s",
                                typeSearch, target, Thread.currentThread().getName()));
                break;
            }
        }
        return item;
    }

    public Item searchItemByIndex() {
        if (arr.length <= 10) {
            return processing("SEQUENTIAL SEARCH", 0, arr.length);
        }
        return pool.invoke(this);
    }

    @Override
    protected Item compute() {
        if (from == to) {
            Item x = processing("PARALLEL SEARCH", from, to);
            System.out.println(this + " - " + x);
            return x;
        }
        int mid = (to + from) / 2;
        Fork firstHalf = new Fork(from, mid, arr, target);
        Fork secondHalf = new Fork(mid + 1, to, arr, target);
        ForkJoinTask.invokeAll(firstHalf, secondHalf);
        Item second = secondHalf.join();
        Item first = firstHalf.join();
        return first != null ? first : second;
    }

    @Override
    public String toString() {
        return "Fork{" + "from=" + from + ", to=" + to + '}';
    }
}
