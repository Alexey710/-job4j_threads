package ru.job4j.concurrent.synchronize.cas.cache;

public class OptimisticException extends RuntimeException {

    public OptimisticException(String message) {
        super(message);
    }
}
