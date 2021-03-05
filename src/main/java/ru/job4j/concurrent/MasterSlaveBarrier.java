package ru.job4j.concurrent;

public class MasterSlaveBarrier {
    private boolean flag = false;

    public void tryMaster() {
        doneMaster();
    }

    public void trySlave() {
        doneSlave();
    }

    public void doneMaster() {
        synchronized (this) {
            if (!flag) {
                System.out.println("Thread A");
            }
            flag = true;
            notify();
        }
    }

    public void doneSlave() {
        synchronized (this) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread B");
                flag = false;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        MasterSlaveBarrier masterSlaveBarrier = new MasterSlaveBarrier();
        Thread first = new Thread(
                () -> {
                    while (true) {
                        masterSlaveBarrier.trySlave();
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        Thread second = new Thread(
                () -> {
                    while (true) {
                        masterSlaveBarrier.tryMaster();
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        first.start();
        second.start();
        first.join();
        second.join();
    }
}
