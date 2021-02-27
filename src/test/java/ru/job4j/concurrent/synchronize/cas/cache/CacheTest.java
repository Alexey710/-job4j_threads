package ru.job4j.concurrent.synchronize.cas.cache;

import org.junit.Assert;
import org.junit.Test;

public class CacheTest {

    @Test
    public void add() {
        Cache cache = new Cache();
        Base base = new Base(1, 0);
        Assert.assertTrue(cache.add(base));
    }

    @Test
    public void updateValidVersion() {
        Cache cache = new Cache();
        Base base = new Base(1, 0);
        cache.add(base);
        Assert.assertTrue(cache.update(base));
    }

    @Test (expected = OptimisticException.class)
    public void updateInvalidVersion() {
        Cache cache = new Cache();
        Base base = new Base(1, 0);
        cache.add(base);
        cache.update(base);
        cache.update(base);
    }

    @Test
    public void delete() {
        Cache cache = new Cache();
        Base base = new Base(1, 0);
        cache.add(base);
        cache.delete(base);
        Assert.assertFalse(cache.update(base));
    }
}