package ru.job4j.concurrent.synchronize;

import ru.job4j.collection.SimpleArray;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class IteratorFailSafe<T> implements Iterator {
    private final Object[] container;
    private int point = 0;
    private final int sizeContainer;

    public IteratorFailSafe(SimpleArray<T> simpleArray, int size) {
        this.container = simpleArray.getContainer();
        sizeContainer = size;
    }

    public boolean hasNext() {
        return point < sizeContainer;
    }

    public Object next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return container[point++];
    }
}
