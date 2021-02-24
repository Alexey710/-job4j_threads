package ru.job4j.concurrent.synchronize.storage;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserStorageTest {
    @Test
    public void testAdd() {
        UserStorage storage = new UserStorage();
        assertTrue(storage.add(new User(1, 100)));
        assertFalse(storage.add(new User(1, 100)));
    }

    @Test
    public void testUpdate() {
        UserStorage storage = new UserStorage();
        User user1 = new User(1, 100);
        storage.add(user1);
        assertTrue(storage.update(new User(1, 200)));
        assertFalse(storage.update(new User(2, 300)));
    }

    @Test
    public void testDelete() {
        UserStorage storage = new UserStorage();
        User user1 = new User(1, 100);
        storage.add(user1);
        assertTrue(storage.delete(user1));
        assertFalse(storage.delete(user1));
    }

    @Test
    public void testTransfer() {
        UserStorage storage = new UserStorage();
        storage.add(new User(1, 100));
        storage.add(new User(2, 200));
        storage.transfer(1, 2, 50);
        assertEquals(storage.getMap().get(1).getAmount(), 50);
    }

    @Test (expected = UnsupportedOperationException.class)
    public void testGetMap() {
        UserStorage storage = new UserStorage();
        storage.add(new User(1, 100));
        storage.getMap().remove(1);
    }
}