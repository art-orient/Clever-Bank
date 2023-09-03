package com.art.clever.model.dao;

import com.art.clever.exception.CleverDatabaseException;
import com.art.clever.model.entity.Account;
import com.art.clever.model.entity.Transaction;
import com.art.clever.model.entity.User;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * The {@code TransactionalDao} interface for working with database table transactions
 *
 * @author Aliaksandr Artsikhovich
 * @version 1.0
 */
public interface TransactionDao {

    /**
     * Saves the transaction
     *
     * @param transaction {@link Transaction} the transaction
     * @return the boolean is the transaction saved
     * @throws CleverDatabaseException the CleverDatabaseException exception
     */
    boolean add(Transaction transaction) throws CleverDatabaseException;

    /**
     * Finds all transactions
     *
     * @return {@link List} of {@link Transaction} the list of found transactions
     * @throws CleverDatabaseException the CleverDatabaseException exception
     */
    List<Transaction> getAll() throws CleverDatabaseException;

    /**
     * Finds the transactions by user account and time period
     *
     * @param account {@link Account} the account of the user who did the transaction
     * @param since {@link Instant} start of period for statistics
     * @param till {@link Instant} end of period for statistics
     * @return {@link List} of {@link Transaction} the list of found transactions
     * @throws CleverDatabaseException the CleverDatabaseException exception
     */
    List<Transaction> getByAccountAndTimePeriod(Account account, Instant since, Instant till)
            throws CleverDatabaseException;

    /**
     * Finds the transaction by its number
     *
     * @param actionId {@link User} the number of the transaction from the database
     * @return {@link Optional} of {@link Transaction} the optional of found transaction
     * @throws CleverDatabaseException the CleverDatabaseException exception
     */
    Optional<Transaction> getByNumber(long actionId) throws CleverDatabaseException;

    /**
     * Changes the transaction data
     *
     * @param transaction {@link Transaction} the transaction
     * @return the boolean is the account updated
     * @throws CleverDatabaseException the CleverDatabaseException exception
     */
    boolean update(Transaction transaction) throws CleverDatabaseException;

    /**
     * Removes the transaction by its number
     *
     * @param actionId {@code long} the number of the transaction
     * @return the boolean is the account deleted
     * @throws CleverDatabaseException the CleverDatabaseException exception
     */
    boolean delete(long actionId) throws CleverDatabaseException;
}
