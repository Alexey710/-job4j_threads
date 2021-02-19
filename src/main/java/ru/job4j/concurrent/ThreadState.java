package ru.job4j.concurrent;

import static java.lang.Thread.State.TERMINATED;

public class ThreadState {
    public static void main(String[] args) {
        Thread first = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName());
                }
        );
        Thread second = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName());
                }
        );
        System.out.println(first.getState());
        first.start();
        second.start();
        while (!(first.getState() == TERMINATED && second.getState() == TERMINATED)) {
        }
        System.out.println(String.format("%s и %s завершили работу",
                first.getName(), second.getName()));
    }
}