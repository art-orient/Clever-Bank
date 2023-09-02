package com.art.clever.service;

import com.art.clever.exception.ServiceException;
import com.art.clever.model.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * The {@code UserService} interface represents user service
 *
 * @author Aliaksandr Artsikhovich
 * @version 1.0
 */
public interface UserService {

    /**
     * Registers a new user
     *
     * @param user {@link User} the user
     * @return the boolean
     * @throws ServiceException the ServiceException exception
     */
    boolean addUser(User user) throws ServiceException;

    /**
     * Finds all users
     *
     * @return {@link List} of {@link User} the list of found users
     * @throws ServiceException the ServiceException exception
     */
    List<User> findUsers() throws ServiceException;

    /**
     * Finds the user by his passport id number
     *
     * @param passportId {@link String} the passport id number
     * @return {@link Optional} of {@link User} the optional of found user
     * @throws ServiceException the ServiceException exception
     */
    Optional<User> findUserByPassportId(String passportId) throws ServiceException;

    /**
     * Finds the user by his lastname
     *
     * @param lastname {@link String} the username
     * @return {@link Optional} of {@link User} the optional of found user
     * @throws ServiceException the ServiceException exception
     */
    Optional<User> findUserByLastname(String lastname) throws ServiceException;

    /**
     * Updates the user
     *
     * @param user {@link String} the user
     * @return the boolean
     * @throws ServiceException the ServiceException exception
     */
    boolean updateUser(User user) throws ServiceException;

    /**
     * Removes the user by his passport id number
     *
     * @param passportId {@link String} the username
     * @return the boolean
     * @throws ServiceException the ServiceException exception
     */
    boolean deleteUser(String passportId) throws ServiceException;
}
