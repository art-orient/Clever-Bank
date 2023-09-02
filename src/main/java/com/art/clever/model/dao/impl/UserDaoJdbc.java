package com.art.clever.model.dao.impl;

import com.art.clever.exception.CleverDatabaseException;
import com.art.clever.model.dao.UserDao;
import com.art.clever.model.entity.User;
import com.art.clever.model.pool.ConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.art.clever.model.dao.column.UsersColumn.FIRSTNAME;
import static com.art.clever.model.dao.column.UsersColumn.FIRSTNAME_INDEX;
import static com.art.clever.model.dao.column.UsersColumn.LASTNAME;
import static com.art.clever.model.dao.column.UsersColumn.LASTNAME_INDEX;
import static com.art.clever.model.dao.column.UsersColumn.PASSPORT_ID;
import static com.art.clever.model.dao.column.UsersColumn.PASSPORT_ID_INDEX;
import static com.art.clever.model.dao.column.UsersColumn.SURNAME;
import static com.art.clever.model.dao.column.UsersColumn.SURNAME_INDEX;

/**
 * The {@code UserDaoJdbc} class works with database table users
 *
 * @author Aliaksandr Artsikhovich
 * @version 1.0
 * @see UserDao
 */
public class UserDaoJdbc implements UserDao {
    private static final Logger logger = LogManager.getLogger();

    private static final UserDaoJdbc INSTANCE = new UserDaoJdbc();
    private static final String INSERT_USER = "INSERT INTO users (passport, lastname, firstname, surname) " +
            "VALUES (?, ?, ?, ?)";
    private static final String SELECT_ALL = "SELECT passport, lastname, firstname, surname FROM users";
    private static final String CHECK_BY_PASSPORT_ID = "SELECT passport FROM users WHERE passport = ?";
    private static final String GET_USER_BY_LASTNAME = SELECT_ALL + " WHERE lastname = ?";
    private static final String GET_USER_BY_PASSPORT_ID = SELECT_ALL + " WHERE passport = ?";
    private static final String DELETE_USER = "DELETE FROM users WHERE passport = ?";
    private static final String UPDATE_USER = "UPDATE users SET lastname = ?, firstname = ?, lastname = ? " +
            "WHERE passport = ?";
    private static final int UPDATE_PASSPORT_ID_INDEX = 4;

    private UserDaoJdbc() {
    }

    public static UserDaoJdbc getInstance() {
        return INSTANCE;
    }

    /**
     * Saves the user to the database
     *
     * @param user {@link User} the user
     * @return the boolean is the user saved
     * @throws CleverDatabaseException the CleverDatabaseException exception
     */
    @Override
    public boolean add(User user) throws CleverDatabaseException {
        boolean isAddUser;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER)) {
            preparedStatement.setString(PASSPORT_ID_INDEX, user.getPassportId());
            preparedStatement.setString(LASTNAME_INDEX, user.getLastName());
            preparedStatement.setString(FIRSTNAME_INDEX, user.getFirstName());
            preparedStatement.setString(SURNAME_INDEX, user.getSurName());
            isAddUser = (preparedStatement.executeUpdate() == 1);
            logger.log(Level.INFO, () -> "The user is saved in the database");
        } catch (SQLException e) {
            throw new CleverDatabaseException("An error occurred while saving the user to the database", e);
        }
        return isAddUser;
    }

    /**
     * Finds all users
     *
     * @return {@link List} of {@link User} the list of found users
     * @throws CleverDatabaseException the CleverDatabaseException exception
     */
    @Override
    public List<User> getAll() throws CleverDatabaseException {
        List<User> users = new ArrayList<>();
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_ALL)) {
            while (resultSet.next()) {
                User user = buildUser(resultSet);
                users.add(user);
            }
            logger.log(Level.DEBUG, () -> "Users got from the database");
        } catch (SQLException e) {
            throw new CleverDatabaseException("An error occurred while getting users from the database", e);
        }
        return users;
    }

    /**
     * Finds the user by his passport id number
     *
     * @param passportId {@link String} the user's passport id number
     * @return {@link Optional} of {@link User} the optional of found user
     * @throws CleverDatabaseException the CleverDatabaseException exception
     */
    @Override
    public Optional<User> getByPassportId(String passportId) throws CleverDatabaseException {
        Optional<User> optionalUser = Optional.empty();
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_BY_PASSPORT_ID)) {
            preparedStatement.setString(1, passportId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    User user = buildUser(resultSet);
                    optionalUser = Optional.of(user);
                }
            }
            logger.log(Level.DEBUG, () -> String.format("The user with ID = %s got from the database", passportId));
        } catch (SQLException e) {
            throw new CleverDatabaseException("An error occurred while getting the user by his passport ID number " +
                    "from the database", e);
        }
        return optionalUser;
    }

    /**
     * Finds the user by his lastname
     *
     * @param lastname {@link String} the user's lastname
     * @return {@link Optional} of {@link User} the optional of found user
     * @throws CleverDatabaseException the CleverDatabaseException exception
     */
    @Override
    public Optional<User> getByLastName(String lastname) throws CleverDatabaseException {
        Optional<User> optionalUser = Optional.empty();
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_BY_LASTNAME)) {
            preparedStatement.setString(1, lastname);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    User user = buildUser(resultSet);
                    optionalUser = Optional.of(user);
                }
            }
            logger.log(Level.DEBUG, () -> String.format("The user %s got from the database", lastname));
        } catch (SQLException e) {
            throw new CleverDatabaseException("An error occurred while getting the user by his lastname from the " +
                    "database", e);
        }
        return optionalUser;
    }

    /**
     * Changes user data
     *
     * @param user {@link User} the user
     * @return the boolean is the user updated
     * @throws CleverDatabaseException the CleverDatabaseException exception
     */
    @Override
    public boolean update(User user) throws CleverDatabaseException {
        boolean isUserUpdated;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER)) {
            preparedStatement.setString(LASTNAME_INDEX -1, user.getLastName());
            preparedStatement.setString(FIRSTNAME_INDEX - 1, user.getFirstName());
            preparedStatement.setString(SURNAME_INDEX - 1, user.getSurName());
            preparedStatement.setString(UPDATE_PASSPORT_ID_INDEX, user.getPassportId());
            isUserUpdated = (preparedStatement.executeUpdate() == 1);
            logger.log(Level.INFO, "The user {} is updated", user.getPassportId());
        } catch (SQLException e) {
            throw new CleverDatabaseException("An error occurred while updating the user", e);
        }
        return isUserUpdated;
    }

    /**
     * Removes the user by his passport id number
     *
     * @param passportId {@link String} the passport id number
     * @return the boolean is the user deleted
     * @throws CleverDatabaseException the CleverDatabaseException exception
     */
    @Override
    public boolean delete(String passportId) throws CleverDatabaseException {
        boolean isUserDeleted;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER)) {
            preparedStatement.setString(PASSPORT_ID_INDEX, passportId);
            isUserDeleted = (preparedStatement.executeUpdate() == 1);
            logger.log(Level.INFO, "The user with ID = {} is deleted", passportId);
        } catch (SQLException e) {
            throw new CleverDatabaseException("An error occurred while deleting users from the database", e);
        }
        return isUserDeleted;
    }

    /**
     * Сhecks if the user has already registered
     *
     * @param passportId {@link String} the passport id number of the user
     * @return the boolean is the username busy
     * @throws CleverDatabaseException the CleverDatabaseException exception
     */
    @Override
    public boolean checkIsUserRegistered(String passportId) throws CleverDatabaseException {
        boolean isUserRegistered = false;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CHECK_BY_PASSPORT_ID)) {
            preparedStatement.setString(1, passportId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    isUserRegistered = true;
                }
            }
            logger.log(Level.DEBUG, () -> String.format("This user with ID = %s is already present in the database",
                    passportId));
        } catch (SQLException e) {
            throw new CleverDatabaseException("This user has already registered in the database", e);
        }
        return isUserRegistered;
    }

    /**
     * Creates the user from resultSet
     *
     * @param resultSet {@link ResultSet} the resultSet
     * @return {@link User} the user
     * @throws SQLException the SQLException exception
     */
    private User buildUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setPassportId(resultSet.getString(PASSPORT_ID));
        user.setLastName(resultSet.getString(LASTNAME));
        user.setFirstName(resultSet.getString(FIRSTNAME));
        user.setSurName(resultSet.getString(SURNAME));
        return user;
    }
}
