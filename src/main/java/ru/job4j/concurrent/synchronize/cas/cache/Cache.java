package ru.job4j.concurrent.synchronize.cas.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) {
        Base stored = memory.get(model.getId());
        if (stored.getVersion() != model.getVersion()) {
            throw new OptimisticException("Versions are not equal");
        }
        return memory.computeIfPresent(
                model.getId(),
                (key, value) -> {
                    Base base = new Base(model.getId(), value.getVersion() + 1);
                    base.setName(model.getName());
                    return base;
                }
        ) != null;
    }

    public void delete(Base model) {
        memory.remove(model.getId(), model);
    }
}
