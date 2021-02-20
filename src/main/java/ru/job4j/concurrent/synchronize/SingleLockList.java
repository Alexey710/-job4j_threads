package ru.job4j.concurrent.synchronize;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import ru.job4j.collection.SimpleArray;

import java.util.Iterator;

@ThreadSafe
public class SingleLockList<T> implements Iterable<T> {
    @GuardedBy("this")
    private volatile SimpleArray<T> list = new SimpleArray<>() {
        @Override
        public synchronized Iterator<T> iterator() {
            return new IteratorFailSafe<T>(this, list.getSize());
        }
    };

    public synchronized void add(T value) {
        list.add(value);
    }

    public synchronized T get(int index) {
        return list.get(index);
    }

    private synchronized SimpleArray<T> copy(SimpleArray<T> list) {
        return list;
    }

    @Override
    public synchronized Iterator<T> iterator() {
        return copy(list).iterator();
    }
}
