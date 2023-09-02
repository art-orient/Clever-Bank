package com.art.clever.model.dao.impl;

import com.art.clever.exception.CleverDatabaseException;
import com.art.clever.model.dao.BankDao;
import com.art.clever.model.entity.Bank;
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

import static com.art.clever.model.dao.column.BanksColumn.BANK_NAME;
import static com.art.clever.model.dao.column.BanksColumn.BANK_NAME_INDEX;
import static com.art.clever.model.dao.column.BanksColumn.BIC_CODE;
import static com.art.clever.model.dao.column.BanksColumn.BIC_CODE_INDEX;

/**
 * The {@code BankDaoJdbc} class works with database table banks
 *
 * @author Aliaksandr Artsikhovich
 * @version 1.0
 * @see BankDao
 */
public class BankDaoJdbc implements BankDao {
    private static final Logger logger = LogManager.getLogger();

    private static final BankDaoJdbc INSTANCE = new BankDaoJdbc();
    private static final String INSERT_BANK = "INSERT INTO banks (bic_code, bank_name) VALUES (?, ?)";
    private static final String SELECT_ALL = "SELECT bic_code, bank_name FROM banks";
    private static final String GET_BANK_BY_BIC_CODE = SELECT_ALL + " WHERE bic_code = ?";
    private static final String GET_BANK_BY_NAME = SELECT_ALL + " WHERE bank_name = ?";
    private static final String DELETE_BANK = "DELETE FROM banks WHERE bic_code = ?";
    private static final String UPDATE_BANK = "UPDATE banks SET bank_name = ? WHERE bic_code = ?";
    private static final int UPDATE_BIC_CODE_INDEX = 2;

    private BankDaoJdbc() {
    }

    public static BankDaoJdbc getInstance() {
        return INSTANCE;
    }

    /**
     * Saves the bank
     *
     * @param bank {@link Bank} the bank
     * @return the boolean is the bank saved
     * @throws CleverDatabaseException the CleverDatabaseException exception
     */
    @Override
    public boolean add(Bank bank) throws CleverDatabaseException {
        boolean isAddBank;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_BANK)) {
            preparedStatement.setString(BIC_CODE_INDEX, bank.getCodeBic());
            preparedStatement.setString(BANK_NAME_INDEX, bank.getName());
            isAddBank = (preparedStatement.executeUpdate() == 1);
            logger.log(Level.INFO, () -> "The bank is saved in the database");
        } catch (SQLException e) {
            throw new CleverDatabaseException("An error occurred while saving the bank in the database", e);
        }
        return isAddBank;
    }

    /**
     * Finds all banks
     *
     * @return {@link List} of {@link Bank} the list of found banks
     * @throws CleverDatabaseException the CleverDatabaseException exception
     */
    @Override
    public List<Bank> getAll() throws CleverDatabaseException {
        List<Bank> banks = new ArrayList<>();
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_ALL)) {
            while (resultSet.next()) {
                Bank bank = new Bank();
                bank.setCodeBic(resultSet.getString(BIC_CODE));
                bank.setName(resultSet.getString(BANK_NAME));
                banks.add(bank);
            }
            logger.log(Level.DEBUG, () -> "All banks have got from the database");
        } catch (SQLException e) {
            throw new CleverDatabaseException("An error occurred while getting banks from the database", e);
        }
        return banks;
    }

    /**
     * Finds the bank by his BIC code
     *
     * @param bicCode {@link String} the BIC code of the bank
     * @return {@link Optional} of {@link Bank} the optional of the found bank
     * @throws CleverDatabaseException the CleverDatabaseException exception
     */
    @Override
    public Optional<Bank> getByBicCode(String bicCode) throws CleverDatabaseException {
        Optional<Bank> optionalBank = Optional.empty();
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_BANK_BY_BIC_CODE)) {
            preparedStatement.setString(1, bicCode);
            optionalBank = fillOptionalBank(optionalBank, preparedStatement);
            logger.log(Level.DEBUG, () -> String.format("The bank with BIC code = %s got from the database",
                    bicCode));
        } catch (SQLException e) {
            throw new CleverDatabaseException("An error occurred while getting the bank by his BIC code " +
                    "from the database", e);
        }
        return optionalBank;
    }

    /**
     * Finds the bank by his name
     *
     * @param bankName {@link String} the name of the bank
     * @return {@link Optional} of {@link Bank} the optional of the found bank
     * @throws CleverDatabaseException the CleverDatabaseException exception
     */
    @Override
    public Optional<Bank> getByBankName(String bankName) throws CleverDatabaseException {
        Optional<Bank> optionalBank = Optional.empty();
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_BANK_BY_NAME)) {
            preparedStatement.setString(1, bankName);
            optionalBank = fillOptionalBank(optionalBank, preparedStatement);
            logger.log(Level.DEBUG, () -> String.format("The bank %s got from the database", bankName));
        } catch (SQLException e) {
            throw new CleverDatabaseException("An error occurred while getting the bank by his name from the " +
                    "database", e);
        }
        return optionalBank;
    }

    /**
     * Changes bank name
     *
     * @param bank {@link Bank} the bank
     * @return the boolean is the bank updated
     * @throws CleverDatabaseException the CleverDatabaseException exception
     */
    @Override
    public boolean update(Bank bank) throws CleverDatabaseException {
        boolean isBankUpdated;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BANK)) {
            preparedStatement.setString(BANK_NAME_INDEX -1, bank.getName());
            preparedStatement.setString(UPDATE_BIC_CODE_INDEX, bank.getCodeBic());
            isBankUpdated = (preparedStatement.executeUpdate() == 1);
            logger.log(Level.INFO, "The bank {} is updated", bank.getName());
        } catch (SQLException e) {
            throw new CleverDatabaseException("An error occurred while updating the bank", e);
        }
        return isBankUpdated;
    }

    /**
     * Removes the bank by his BIC code
     *
     * @param bicCode {@link String} the BIC code of the bank
     * @return the boolean is the bank deleted
     * @throws CleverDatabaseException the CleverDatabaseException exception
     */
    @Override
    public boolean delete(String bicCode) throws CleverDatabaseException {
        boolean isBankDeleted;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BANK)) {
            preparedStatement.setString(BIC_CODE_INDEX, bicCode);
            isBankDeleted = (preparedStatement.executeUpdate() == 1);
            logger.log(Level.INFO, "The bank with BIC code = {} is deleted", bicCode);
        } catch (SQLException e) {
            throw new CleverDatabaseException("An error occurred while deleting the bank from the database", e);
        }
        return isBankDeleted;
    }

    /**
     * Extracts the bank from the resultSet
     *
     * @param optionalBank {@link Optional} of {@link Bank} the bank empty data
     * @param preparedStatement {@link PreparedStatement} statement of JDBC
     * @return {@link Optional} of {@link Bank} the optional of the found bank
     */
    private Optional<Bank> fillOptionalBank(Optional<Bank> optionalBank, PreparedStatement preparedStatement)
            throws SQLException {
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                Bank bank = new Bank();
                bank.setCodeBic(resultSet.getString(BIC_CODE));
                bank.setName(resultSet.getString(BANK_NAME));
                optionalBank = Optional.of(bank);
            }
        }
        return optionalBank;
    }
}
