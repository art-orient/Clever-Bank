package com.art.clever.model.dao;

import com.art.clever.exception.CleverDatabaseException;
import com.art.clever.exception.ConnectionPoolException;
import com.art.clever.model.dao.impl.UserDaoJdbc;
import com.art.clever.model.entity.User;
import com.art.clever.model.pool.ConnectionPool;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserDaoTest {
    private static UserDao userDao;
    private static final ConnectionPool POOL = ConnectionPool.INSTANCE;
    private static User user;

    @BeforeAll
    static void init() throws ConnectionPoolException {
        POOL.initPool();
        userDao = UserDaoJdbc.getInstance();
        user = new User();
        user.setPassportId("3220575A053BN5");
        user.setLastName("Романович");
        user.setFirstName("Алексей");
        user.setSurName("Алексеевмч");
    }

    @AfterAll
    static void teardown() throws ConnectionPoolException {
        POOL.destroyPool();
    }

    @Test
    void addUserTest() throws CleverDatabaseException {
        userDao.add(user);
        User userFromDb = userDao.getByPassportId(user.getPassportId()).get();
        assertEquals("Романович", userFromDb.getLastName(), "Lastnames must be equals");
        userDao.delete(user.getPassportId());
    }

    @Test
    void getAllUsersTest() throws CleverDatabaseException {
        userDao.add(user);
        List<User> users = userDao.getAll();
        assertTrue(users.size() > 0);
        assertTrue(users.contains(user));
        userDao.delete(user.getPassportId());
    }

    @Test
    void getUserByPassportIdTest() throws CleverDatabaseException {
        userDao.add(user);
        User userFromDb = userDao.getByPassportId(user.getPassportId()).get();
        assertEquals(userFromDb.getLastName(), user.getLastName());
        assertEquals(userFromDb.getFirstName(), user.getFirstName());
        if (user.getSurName() != null) {
            assertEquals(userFromDb.getSurName(), user.getSurName());
        }
        userDao.delete(user.getPassportId());
    }

    @Test
    void getUserByLastnameTest() throws CleverDatabaseException {
        userDao.add(user);
        User userFromDb = userDao.getByLastName(user.getLastName()).get();
        assertEquals(userFromDb.getPassportId(), user.getPassportId());
        assertEquals(userFromDb.getLastName(), user.getLastName());
        assertEquals(userFromDb.getFirstName(), user.getFirstName());
        if (user.getSurName() != null) {
            assertEquals(userFromDb.getSurName(), user.getSurName());
        }
        userDao.delete(user.getPassportId());
    }

    @Test
    void updateUserTest() throws CleverDatabaseException {
        userDao.add(user);
        user.setLastName("Потапов");
        userDao.update(user);
        User userFromDb = userDao.getByPassportId(user.getPassportId()).get();
        assertEquals("Потапов", userFromDb.getLastName(), "Updated lastnames must be equal.");
        userDao.delete(user.getPassportId());
    }

    @Test
    void deleteUserTest() throws CleverDatabaseException {
        userDao.add(user);
        User userFromDb = userDao.getByPassportId(user.getPassportId()).get();
        assertNotNull(userFromDb, "User must be saved");
        userDao.delete(user.getPassportId());
        assertTrue(userDao.getByPassportId(user.getPassportId()).isEmpty(), "User should be null");
    }

    @Test
    void checkIsUserRegisteredTest() throws CleverDatabaseException {
        userDao.add(user);
        boolean isPresent = userDao.checkIsUserRegistered(user.getPassportId());
        assertTrue(isPresent, "User must be saved");
        userDao.delete(user.getPassportId());
        isPresent = userDao.checkIsUserRegistered(user.getPassportId());
        assertFalse(isPresent, "User should be deleted");
    }
}
