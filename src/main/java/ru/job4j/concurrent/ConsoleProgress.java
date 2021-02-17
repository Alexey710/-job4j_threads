package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                char[] process = {'\\',  '|',  '/'};
                for (int i = 0; i < process.length; i++) {
                    Thread.sleep(500);
                    System.out.print("\r load: " + process[i]);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args)  {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        try {
            Thread.sleep(1000); /* симулируем выполнение параллельной задачи в течение 1 секунды. */
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        progress.interrupt();

    }

}
