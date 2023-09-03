package com.art.clever.model.dao.impl;

import com.art.clever.exception.CleverDatabaseException;
import com.art.clever.model.dao.AccountDao;
import com.art.clever.model.dao.BankDao;
import com.art.clever.model.dao.UserDao;
import com.art.clever.model.entity.Account;
import com.art.clever.model.entity.Bank;
import com.art.clever.model.entity.Currency;
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
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.art.clever.model.dao.column.AccountsColumn.BALANCE;
import static com.art.clever.model.dao.column.AccountsColumn.BALANCE_INDEX;
import static com.art.clever.model.dao.column.AccountsColumn.BANK_BIC_CODE;
import static com.art.clever.model.dao.column.AccountsColumn.BANK_BIC_CODE_INDEX;
import static com.art.clever.model.dao.column.AccountsColumn.CODE_IBAN;
import static com.art.clever.model.dao.column.AccountsColumn.CODE_IBAN_INDEX;
import static com.art.clever.model.dao.column.AccountsColumn.CREATED_AT;
import static com.art.clever.model.dao.column.AccountsColumn.CREATED_AT_INDEX;
import static com.art.clever.model.dao.column.AccountsColumn.CURRENCY;
import static com.art.clever.model.dao.column.AccountsColumn.CURRENCY_INDEX;
import static com.art.clever.model.dao.column.AccountsColumn.USER_ID;
import static com.art.clever.model.dao.column.AccountsColumn.USER_ID_INDEX;

/**
 * The {@link AccountDaoJdbc} class works with database table accounts
 *
 * @author Aliaksandr Artsikhovich
 * @version 1.0
 */
public class AccountDaoJdbc implements AccountDao {

    private static final Logger logger = LogManager.getLogger();

    private static final AccountDaoJdbc INSTANCE = new AccountDaoJdbc();
    private static final String INSERT_ACCOUNT = "INSERT INTO accounts (code_iban, bank_bic_code, user_passport_id, " +
            "currency, created_at, balance) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SELECT_ALL = "SELECT * FROM accounts";
    private static final String GET_BY_IBAN = SELECT_ALL + " WHERE code_iban = ?";
    private static final String GET_ALL_FOR_USER = SELECT_ALL + " WHERE user_passport_id = ?";
    private static final String GET_ALL_BY_BANK = SELECT_ALL + " WHERE bank_bic_code = ?";
    private static final String UPDATE_ACCOUNT = "UPDATE accounts SET bank_bic_code = ?, user_passport_id = ?, " +
            "currency = ?, created_at = ?, balance = ? WHERE code_iban = ?";
    private static final String DELETE_ACCOUNT = "DELETE FROM accounts WHERE code_iban = ?";
    private final BankDao bankDao = BankDaoJdbc.getInstance();
    private final UserDao userDao = UserDaoJdbc.getInstance();

    private AccountDaoJdbc() {
    }

    public static AccountDaoJdbc getInstance() {
        return INSTANCE;
    }

    /**
     * Saves the account
     *
     * @param account {@link Account} the bank account
     * @return the boolean is the account saved
     * @throws CleverDatabaseException the CleverDatabaseException exception
     */
    @Override
    public boolean add(Account account) throws CleverDatabaseException {
        boolean isAddAccount;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ACCOUNT)) {
            preparedStatement.setString(CODE_IBAN_INDEX, account.getCodeIBAN());
            preparedStatement.setString(BANK_BIC_CODE_INDEX, account.getBank().getCodeBic());
            preparedStatement.setString(USER_ID_INDEX, account.getUser().getPassportId());
            preparedStatement.setString(CURRENCY_INDEX, account.getCurrency().name());
            preparedStatement.setObject(CREATED_AT_INDEX, account.getCreatedAt());
            preparedStatement.setBigDecimal(BALANCE_INDEX, account.getBalance());
            isAddAccount = (preparedStatement.executeUpdate() == 1);
            logger.log(Level.INFO, () -> "The account is saved in the database");
        } catch (SQLException e) {
            throw new CleverDatabaseException("An error occurred while saving the account to the database", e);
        }
        return isAddAccount;
    }

    /**
     * Finds all accounts
     *
     * @return {@link List} of {@link Account} the list of found accounts
     * @throws CleverDatabaseException the CleverDatabaseException exception
     */
    @Override
    public List<Account> getAll() throws CleverDatabaseException {
        List<Account> accounts = new ArrayList<>();
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_ALL)) {
            while (resultSet.next()) {
                Account account = extractAccount(resultSet);
                accounts.add(account);
            }
            logger.log(Level.DEBUG, () -> "Accounts got from the database");
        } catch (SQLException e) {
            throw new CleverDatabaseException("An error occurred while getting all accounts from the database", e);
        }
        return accounts;
    }

    /**
     * Finds the account by his IBAN number
     *
     * @param codeIBAN {@link String} the user's passport id number
     * @return {@link Optional} of {@link Account} the optional of found account
     * @throws CleverDatabaseException the CleverDatabaseException exception
     */
    @Override
    public Optional<Account> getByCodeIBAN(String codeIBAN) throws CleverDatabaseException {
        Optional<Account> optionalAccount = Optional.empty();
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_IBAN)) {
            preparedStatement.setString(1, codeIBAN);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Account account = extractAccount(resultSet);
                    optionalAccount = Optional.of(account);
                }
            }
            logger.log(Level.DEBUG, () -> String.format("The account with IBAN = %s got from the database",
                    codeIBAN));
        } catch (SQLException e) {
            throw new CleverDatabaseException("An error occurred while getting the account by its IBAN code " +
                    "from the database", e);
        }
        return optionalAccount;
    }

    /**
     * Finds all accounts of the user
     *
     * @param user {@link User} the owner of the account
     * @return {@link List} of {@link Account} the list of found accounts of the user
     * @throws CleverDatabaseException the CleverDatabaseException exception
     */
    @Override
    public List<Account> getAllForUser(User user) throws CleverDatabaseException {
        List<Account> accounts = new ArrayList<>();
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(GET_ALL_FOR_USER)) {
            while (resultSet.next()) {
                Account account = extractAccount(resultSet);
                accounts.add(account);
            }
            logger.log(Level.DEBUG, () -> "Accounts of the user got from the database");
        } catch (SQLException e) {
            throw new CleverDatabaseException("An error occurred while getting accounts from the database", e);
        }
        return accounts;
    }

    /**
     * Finds all accounts of the bank
     *
     * @param bank {@link Bank} the bank which has a list of accounts
     * @return {@link List} of {@link Account} the list of found accounts of the bank
     * @throws CleverDatabaseException the CleverDatabaseException exception
     */
    @Override
    public List<Account> getAllForBank(Bank bank) throws CleverDatabaseException {
        List<Account> accounts = new ArrayList<>();
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(GET_ALL_BY_BANK)) {
            while (resultSet.next()) {
                Account account = extractAccount(resultSet);
                accounts.add(account);
            }
            logger.log(Level.DEBUG, () -> "Accounts of the bank got from the database");
        } catch (SQLException e) {
            throw new CleverDatabaseException("An error occurred while getting accounts of the bank " +
                    "from the database", e);
        }
        return accounts;
    }

    /**
     * Changes the account data
     *
     * @param account {@link Account} the bank account
     * @return the boolean is the account updated
     * @throws CleverDatabaseException the CleverDatabaseException exception
     */
    @Override
    public boolean update(Account account) throws CleverDatabaseException {
        boolean isAccountUpdated;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ACCOUNT)) {
            preparedStatement.setString(BANK_BIC_CODE_INDEX -1, account.getBank().getCodeBic());
            preparedStatement.setString(USER_ID_INDEX - 1, account.getUser().getPassportId());
            preparedStatement.setString(CURRENCY_INDEX - 1, account.getCurrency().name());
            preparedStatement.setObject(CREATED_AT_INDEX, account.getCreatedAt());
            preparedStatement.setBigDecimal(BALANCE_INDEX, account.getBalance());
            isAccountUpdated = (preparedStatement.executeUpdate() == 1);
            logger.log(Level.INFO, "The account {} is updated", account.getCodeIBAN());
        } catch (SQLException e) {
            throw new CleverDatabaseException("An error occurred while updating the account", e);
        }
        return isAccountUpdated;
    }

    /**
     * Removes the account by his IBAN number
     *
     * @param codeIBAN {@link String} the IBAN code of the account
     * @return the boolean is the account deleted
     * @throws CleverDatabaseException the CleverDatabaseException exception
     */
    @Override
    public boolean delete(String codeIBAN) throws CleverDatabaseException {
        boolean isAccountDeleted;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ACCOUNT)) {
            preparedStatement.setString(CODE_IBAN_INDEX, codeIBAN);
            isAccountDeleted = (preparedStatement.executeUpdate() == 1);
            logger.log(Level.INFO, "The account with IBAN = {} is deleted", codeIBAN);
        } catch (SQLException e) {
            throw new CleverDatabaseException("An error occurred while deleting the account from the database", e);
        }
        return isAccountDeleted;
    }

    /**
     * Creates the account from resultSet
     *
     * @param resultSet {@link ResultSet} the resultSet
     * @return {@link Account} the user
     * @throws SQLException the SQLException exception
     */
    private Account extractAccount(ResultSet resultSet) throws SQLException, CleverDatabaseException {
        Account account = new Account();
        account.setCodeIBAN(resultSet.getString(CODE_IBAN));
        account.setBank(bankDao.getByBicCode(resultSet.getString(BANK_BIC_CODE)).orElse(null));
        account.setUser(userDao.getByPassportId(resultSet.getString(USER_ID)).orElse(null));
        account.setCurrency(Currency.valueOf(resultSet.getString(CURRENCY)));
        account.setCreatedAt(resultSet.getObject(CREATED_AT, Instant.class));
        account.setBalance(resultSet.getBigDecimal(BALANCE));
        return account;
    }
}
