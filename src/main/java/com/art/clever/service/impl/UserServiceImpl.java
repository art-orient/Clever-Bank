package com.art.clever.service.impl;

import com.art.clever.exception.CleverDatabaseException;
import com.art.clever.exception.ServiceException;
import com.art.clever.model.dao.UserDao;
import com.art.clever.model.dao.impl.UserDaoJdbc;
import com.art.clever.model.entity.User;
import com.art.clever.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

/**
 * The {@code UserServiceImpl} class represents user service implementation
 *
 * @author Aliaksandr Artsikhovich
 * @version 1.0
 * @see UserService
 */
public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger();
    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }
    /**
     * Registers a new user
     *
     * @param user {@link User} the user
     * @return the boolean
     * @throws ServiceException the service exception
     */
    @Override
    public boolean addUser(User user) throws ServiceException {
        try {
            if (userDao.checkIsUserRegistered(user.getPassportId())) {
                throw new ServiceException("The user has already registered in the database - "
                        + user.getLastName());
            } else {
                return userDao.add(user);
            }
        } catch (CleverDatabaseException e) {
            throw new ServiceException("user registration error", e);
        }
    }

    /**
     * Finds all users
     *
     * @return {@link List} of {@link User} the list of found users
     * @throws ServiceException the service exception
     */
    @Override
    public List<User> findUsers() throws ServiceException {
        try {
            return userDao.getAll();
        } catch (CleverDatabaseException e) {
            throw new ServiceException("An error occurred while retrieving users from the database", e);
        }
    }

    /**
     * Finds the user by his passport id number
     *
     * @param passportId {@link String} the passport id number
     * @return {@link Optional} of {@link User} the optional of found user
     * @throws ServiceException the service exception
     */
    @Override
    public Optional<User> findUserByPassportId(String passportId) throws ServiceException {
        try {
            return userDao.getByPassportId(passportId);
        } catch (CleverDatabaseException e) {
            throw new ServiceException("An error occurred while retrieving the user by his lastname" +
                    " from the database", e);
        }
    }

    /**
     * Finds the user by his lastname
     *
     * @param lastname {@link String} the username
     * @return {@link Optional} of {@link User} the optional of found user
     * @throws ServiceException the service exception
     */
    @Override
    public Optional<User> findUserByLastname(String lastname) throws ServiceException {
        try {
            return userDao.getByLastName(lastname);
        } catch (CleverDatabaseException e) {
            throw new ServiceException("An error occurred while retrieving the user by his lastname" +
                    " from the database", e);
        }
    }

    /**
     * Updates the user
     *
     * @param user {@link String} the user
     * @return the boolean
     * @throws ServiceException the service exception
     */
    @Override
    public boolean updateUser(User user) throws ServiceException {
        try {
            return userDao.update(user);
        } catch (CleverDatabaseException e) {
            throw new ServiceException("An error occurred while updating of user", e);
        }
    }

    /**
     * Removes the user by his passport id number
     *
     * @param passportId {@link String} the username
     * @return the boolean
     * @throws ServiceException the service exception
     */
    @Override
    public boolean deleteUser(String passportId) throws ServiceException {
        try {
            return userDao.delete(passportId);
        } catch (CleverDatabaseException e) {
            throw new ServiceException("An error occurred while deleting of user", e);
        }
    }
}
