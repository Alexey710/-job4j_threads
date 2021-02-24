package ru.job4j.concurrent.synchronize.storage;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ThreadSafe
public class UserStorage implements Store {
    @GuardedBy("this")
    private final Map<Integer, User> map = new ConcurrentHashMap<>();

    public synchronized Map<Integer, User> getMap() {
        return Collections.unmodifiableMap(map);
    }

    private synchronized boolean isExist(User user) {
        User current = map.get(user.getId());
        return current != null;
    }

    @Override
    public synchronized boolean add(User user) {
        if (isExist(user)) {
            return false;
        }
        map.put(user.getId(), user);
        return true;
    }

    @Override
    public synchronized boolean update(User user) {
        if (isExist(user)) {
            add(user);
            return true;
        }
        return false;
    }

    @Override
    public synchronized boolean delete(User user) {
        if (!isExist(user)) {
            return false;
        }
        map.remove(user.getId());
        return true;
    }

    @Override
    public synchronized boolean transfer(int fromId, int toId, int amount) {
        User from = map.get(fromId);
        User to = map.get(toId);
        if (from != null && to != null) {
            if (from.getAmount() >= amount) {
                from.setAmount(from.getAmount() - amount);
                to.setAmount(to.getAmount() + amount);
                return true;
            }
        }
        return false;
    }
}
