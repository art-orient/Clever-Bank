package com.art.clever.service;

import com.art.clever.exception.CleverDatabaseException;
import com.art.clever.exception.ServiceException;
import com.art.clever.model.dao.UserDao;
import com.art.clever.model.entity.User;
import com.art.clever.service.impl.UserServiceImpl;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserDao userDao;
    @InjectMocks
    private UserService userService;
    User user;
    private static List<User> users;

    @BeforeClass
    public void setUp() {
        userDao = mock(UserDao.class);
        userService = new UserServiceImpl(userDao);
        user = new User();
        user.setPassportId("3220570A053BT5");
        user.setLastName("Романов");
        user.setFirstName("Алексей");
        user.setSurName("Алексеевмч");
        users = Arrays.asList(user, new User(), new User(), new User(), new User());
    }

    @Test
    public void addUserPositiveTest() throws CleverDatabaseException, ServiceException {
        when(userDao.checkIsUserRegistered(user.getPassportId())).thenReturn(false);
        when(userDao.add(user)).thenReturn(true);
        assertTrue(userService.addUser(user));
    }

    @Test
    public void addUserNegativeTest() throws CleverDatabaseException {
        when(userDao.checkIsUserRegistered(user.getPassportId())).thenReturn(true);
        assertThrows(ServiceException.class, () -> userService.addUser(user));
    }

    @Test
    public void findUsersTest() throws CleverDatabaseException, ServiceException {
        when(userDao.getAll()).thenReturn(users);
        List<User> usersFromMock = userService.findUsers();
        assertEquals(users.size(), usersFromMock.size());
        assertEquals(users, usersFromMock);
    }

    @Test
    public void findUserByPassportIdTest() throws CleverDatabaseException, ServiceException {
        when(userDao.getByPassportId(user.getPassportId())).thenReturn(Optional.of(user));
        Optional<User> optionalUser = userService.findUserByPassportId(user.getPassportId());
        User userFromMock = optionalUser.orElse(null);
        assert userFromMock != null;
        assertEquals(user.getLastName(), userFromMock.getLastName());
        assertEquals(user.getFirstName(), userFromMock.getFirstName());
    }

    @Test
    public void findUserByLastnameTest() throws CleverDatabaseException, ServiceException {
        when(userDao.getByLastName(user.getLastName())).thenReturn(Optional.of(user));
        Optional<User> optionalUser = userService.findUserByLastname(user.getLastName());
        User userFromMock = optionalUser.orElse(null);
        assert userFromMock != null;
        assertEquals(user.getPassportId(), userFromMock.getPassportId());
        assertEquals(user.getFirstName(), userFromMock.getFirstName());
    }

    @Test
    public void updateUserTest() throws CleverDatabaseException, ServiceException {
        when(userDao.update(user)).thenReturn(true);
        assertTrue(userService.updateUser(user));
    }

    @Test
    public void deleteUserTest() throws CleverDatabaseException, ServiceException {
        when(userDao.delete(user.getPassportId())).thenReturn(true);
        assertTrue(userService.deleteUser(user.getPassportId()));
    }
}
