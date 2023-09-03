package com.art.clever.model.dao.impl;

import com.art.clever.exception.CleverDatabaseException;
import com.art.clever.model.dao.TransactionDao;
import com.art.clever.model.entity.Account;
import com.art.clever.model.entity.Transaction;
import com.art.clever.model.entity.User;
import com.art.clever.model.pool.ConnectionPool;
import com.art.clever.util.Printer;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static com.art.clever.model.dao.column.TransactionColumn.ACTION_TYPE_INDEX;
import static com.art.clever.model.dao.column.TransactionColumn.AMOUNT_INDEX;
import static com.art.clever.model.dao.column.TransactionColumn.COMPLETED_AT_INDEX;
import static com.art.clever.model.dao.column.TransactionColumn.CURRENCY_INDEX;
import static com.art.clever.model.dao.column.TransactionColumn.FROM_IBAN_INDEX;
import static com.art.clever.model.dao.column.TransactionColumn.TO_IBAN_INDEX;

/**
 * The {@code  TransactionDaoJdbc} class works with database table transactions
 *
 * @author Aliaksandr Artsikhovich
 * @version 1.0
 * @see TransactionDao
 */
public class TransactionDaoJdbc implements TransactionDao {

    private static final Logger logger = LogManager.getLogger();

    private static final TransactionDaoJdbc INSTANCE = new TransactionDaoJdbc();
    private static final String INSERT_ACTION = "INSERT INTO transactions (completed_at, transaction_type," +
            " from_IBAN, to_IBAN, currency, amount) VALUES (?, ?, ?, ?, ?, ?)";
    private final Printer printer = new Printer();

    private TransactionDaoJdbc() {
    }

    public static TransactionDaoJdbc getInstance() {
        return INSTANCE;
    }

    /**
     * Saves the transaction
     *
     * @param transaction {@link Transaction} the transaction
     * @return the boolean is the transaction saved
     * @throws CleverDatabaseException the CleverDatabaseException exception
     */
    public boolean add(Transaction transaction) throws CleverDatabaseException {
        boolean isTransactionSaved;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(INSERT_ACTION, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setTimestamp(COMPLETED_AT_INDEX - 1, Timestamp.from(transaction.getTime()));
            preparedStatement.setString(ACTION_TYPE_INDEX - 1, transaction.getTransactionType().name());
            preparedStatement.setString(FROM_IBAN_INDEX - 1, transaction.getFromAccountId());
            preparedStatement.setString(TO_IBAN_INDEX - 1, transaction.getToAccountId());
            preparedStatement.setString(CURRENCY_INDEX - 1, transaction.getCurrency().name());
            preparedStatement.setBigDecimal(AMOUNT_INDEX - 1, transaction.getAmount());
            isTransactionSaved = (preparedStatement.executeUpdate() == 1);
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                long actionId = resultSet.getInt(1);
                transaction.setActionId(actionId);
                logger.log(Level.DEBUG, "actionId = {}", actionId);
            }
            printer.printCheck(transaction);
            logger.log(Level.INFO, () -> "The transaction is saved in the database");
        } catch (SQLException e) {
            throw new CleverDatabaseException("An error occurred while saving the transaction to the database", e);
        }
        return isTransactionSaved;
    }

    /**
     * Finds all transactions
     *
     * @return {@link List} of {@link Transaction} the list of found transactions
     * @throws CleverDatabaseException the CleverDatabaseException exception
     */
    public List<Transaction> getAll() throws CleverDatabaseException {
        return null;
    }

    /**
     * Finds the transactions by user account and time period
     *
     * @param account {@link Account} the account of the user who did the transaction
     * @param since {@link Instant} start of period for statistics
     * @param till {@link Instant} end of period for statistics
     * @return {@link List} of {@link Transaction} the list of found transactions
     * @throws CleverDatabaseException the CleverDatabaseException exception
     */
    public List<Transaction> getByAccountAndTimePeriod(Account account, Instant since, Instant till)
            throws CleverDatabaseException {
        return null;
    }

    /**
     * Finds the transaction by its number
     *
     * @param actionId {@link User} the number of the transaction from the database
     * @return {@link Optional} of {@link Transaction} the optional of found transaction
     * @throws CleverDatabaseException the CleverDatabaseException exception
     */
    public Optional<Transaction> getByNumber(long actionId) throws CleverDatabaseException {
        return Optional.empty();
    }

    /**
     * Changes the transaction data
     *
     * @param transaction {@link Transaction} the transaction
     * @return the boolean is the account updated
     * @throws CleverDatabaseException the CleverDatabaseException exception
     */
    public boolean update(Transaction transaction) throws CleverDatabaseException {
        return false;
    }

    /**
     * Removes the transaction by its number
     *
     * @param actionId {@code long} the number of the transaction
     * @return the boolean is the account deleted
     * @throws CleverDatabaseException the CleverDatabaseException exception
     */
    public boolean delete(long actionId) throws CleverDatabaseException {
        return false;
    }
}
