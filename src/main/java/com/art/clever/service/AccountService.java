package com.art.clever.service;

import com.art.clever.exception.ServiceException;
import com.art.clever.model.entity.Account;
import com.art.clever.model.entity.Bank;
import com.art.clever.model.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * The {@code AccountService} interface represents account service
 *
 * @author Aliaksandr Artsikhovich
 * @version 1.0
 */
public interface AccountService {

    /**
     * Registers a new account
     *
     * @param account {@link Account} the bank account
     * @return the boolean
     * @throws ServiceException the ServiceException exception
     */
    boolean addAccount(Account account) throws ServiceException;

    /**
     * Finds all accounts
     *
     * @return {@link List} of {@link Account} the list of found accounts
     * @throws ServiceException the ServiceException exception
     */
    List<Account> findAllAccounts() throws ServiceException;

    /**
     * Finds the account by his IBAN number
     *
     * @param codeIBAN {@link String} the user's passport id number
     * @return {@link Optional} of {@link Account} the optional of found account
     * @throws ServiceException the ServiceException exception
     */
    Optional<Account> findByCodeIBAN(String codeIBAN) throws ServiceException;

    /**
     * Finds all accounts of the user
     *
     * @param user {@link User} the owner of the account
     * @return {@link List} of {@link Account} the list of found accounts of the user
     * @throws ServiceException the ServiceException exception
     */
    List<Account> findAllForUser(User user) throws ServiceException;

    /**
     * Finds all accounts of the bank
     *
     * @param bank {@link Bank} the bank which has a list of accounts
     * @return {@link List} of {@link Account} the list of found accounts of the bank
     * @throws ServiceException the ServiceException exception
     */
    List<Account> findAllForBank(Bank bank) throws ServiceException;

    /**
     * Changes the account data
     *
     * @param account {@link Account} the bank account
     * @return the boolean is the account updated
     * @throws ServiceException the ServiceException exception
     */
    boolean updateAccount(Account account) throws ServiceException;

    /**
     * Removes the account by his IBAN number
     *
     * @param codeIBAN {@link String} the IBAN code of the account
     * @return the boolean is the account deleted
     * @throws ServiceException the ServiceException exception
     */
    boolean deleteAccount(String codeIBAN) throws ServiceException;
}
