package com.art.clever.model.dao;

import com.art.clever.exception.CleverDatabaseException;
import com.art.clever.model.entity.Account;
import com.art.clever.model.entity.Bank;
import com.art.clever.model.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * The {@code AccountDao} interface for working with database table accounts
 *
 * @author Aliaksandr Artsikhovich
 * @version 1.0
 */
public interface AccountDao {

    /**
     * Saves the account
     *
     * @param account {@link Account} the bank account
     * @return the boolean is the account saved
     * @throws CleverDatabaseException the CleverDatabaseException exception
     */
    boolean add(Account account) throws CleverDatabaseException;

    /**
     * Finds all accounts
     *
     * @return {@link List} of {@link Account} the list of found accounts
     * @throws CleverDatabaseException the CleverDatabaseException exception
     */
    List<Account> getAll() throws CleverDatabaseException;

    /**
     * Finds the account by his IBAN number
     *
     * @param codeIBAN {@link String} the user's passport id number
     * @return {@link Optional} of {@link Account} the optional of found account
     * @throws CleverDatabaseException the CleverDatabaseException exception
     */
    Optional<Account> getByCodeIBAN(String codeIBAN) throws CleverDatabaseException;

    /**
     * Finds all accounts of the user
     *
     * @param user {@link User} the owner of the account
     * @return {@link List} of {@link Account} the list of found accounts of the user
     * @throws CleverDatabaseException the CleverDatabaseException exception
     */
    List<Account> getAllForUser(User user) throws CleverDatabaseException;

    /**
     * Finds all accounts of the bank
     *
     * @param bank {@link Bank} the bank which has a list of accounts
     * @return {@link List} of {@link Account} the list of found accounts of the bank
     * @throws CleverDatabaseException the CleverDatabaseException exception
     */
    List<Account> getAllForBank(Bank bank) throws CleverDatabaseException;

    /**
     * Changes the account data
     *
     * @param account {@link Account} the bank account
     * @return the boolean is the account updated
     * @throws CleverDatabaseException the CleverDatabaseException exception
     */
    boolean update(Account account) throws CleverDatabaseException;

    /**
     * Removes the account by his IBAN number
     *
     * @param codeIBAN {@link String} the IBAN code of the account
     * @return the boolean is the account deleted
     * @throws CleverDatabaseException the CleverDatabaseException exception
     */
    boolean delete(String codeIBAN) throws CleverDatabaseException;

}
