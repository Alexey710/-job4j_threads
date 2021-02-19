package ru.job4j.concurrent.synchronize.storage;

import junit.framework.TestCase;
import org.junit.Assert;

import static org.hamcrest.CoreMatchers.is;

public class UserStorageTest extends TestCase {

    public void testAdd() {
        UserStorage storage = new UserStorage();
        assertTrue(storage.add(new User(1, 100)));
        assertFalse(storage.add(new User(1, 100)));
    }

    public void testUpdate() {
        UserStorage storage = new UserStorage();
        User user1 = new User(1, 100);
        storage.add(user1);
        assertTrue(storage.update(new User(1, 200)));
        assertFalse(storage.update(new User(2, 300)));
    }

    public void testDelete() {
        UserStorage storage = new UserStorage();
        User user1 = new User(1, 100);
        storage.add(user1);
        assertTrue(storage.delete(user1));
        assertFalse(storage.delete(user1));
    }

    public void testTransfer() {
        UserStorage storage = new UserStorage();
        storage.add(new User(1, 100));
        storage.add(new User(2, 200));
        storage.transfer(1, 2, 50);
        assertEquals(storage.getMap().get(1).getAmount(), 50);
    }
}