package com.art.clever.model.dao;

import com.art.clever.exception.CleverDatabaseException;
import com.art.clever.model.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * The {@code UserDao} interface for working with database table users
 *
 * @author Aliaksandr Artsikhovich
 * @version 1.0
 */
public interface UserDao {

    /**
     * Saves the user
     *
     * @param user {@link User} the user
     * @return the boolean is the user saved
     * @throws CleverDatabaseException the CleverDatabaseException exception
     */
    boolean add(User user) throws CleverDatabaseException;

    /**
     * Finds all users
     *
     * @return {@link List} of {@link User} the list of found users
     * @throws CleverDatabaseException the CleverDatabaseException exception
     */
    List<User> getAll() throws CleverDatabaseException;

    /**
     * Finds the user by his passport id number
     *
     * @param passportId {@link String} the user's passport id number
     * @return {@link Optional} of {@link User} the optional of found user
     * @throws CleverDatabaseException the CleverDatabaseException exception
     */
    Optional<User> getByPassportId(String passportId) throws CleverDatabaseException;

    /**
     * Finds the user by his lastname
     *
     * @param lastname {@link String} the user's lastname
     * @return {@link Optional} of {@link User} the optional of found user
     * @throws CleverDatabaseException the CleverDatabaseException exception
     */
    Optional<User> getByLastName(String lastname) throws CleverDatabaseException;

    /**
     * Changes user data
     *
     * @param user {@link User} the user
     * @return the boolean is the user updated
     * @throws CleverDatabaseException the CleverDatabaseException exception
     */
    boolean update(User user) throws CleverDatabaseException;

    /**
     * Removes the user by his passport id number
     *
     * @param passportId {@link String} the passport id number
     * @return the boolean is the user deleted
     * @throws CleverDatabaseException the CleverDatabaseException exception
     */
    boolean delete(String passportId) throws CleverDatabaseException;

    /**
     * Сhecks if the user has already registered
     *
     * @param passportId {@link String} the passport id number of the user
     * @return the boolean is the username busy
     * @throws CleverDatabaseException the CleverDatabaseException exception
     */
    boolean checkIsUserRegistered(String passportId) throws CleverDatabaseException;
}
