package com.art.clever.service.impl;

import com.art.clever.exception.CleverDatabaseException;
import com.art.clever.exception.ServiceException;
import com.art.clever.model.dao.AccountDao;
import com.art.clever.model.entity.Account;
import com.art.clever.model.entity.Bank;
import com.art.clever.model.entity.User;
import com.art.clever.service.AccountService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

/**
 * The {@link AccountServiceImpl} class represents account service implementation
 *
 * @author Aliaksandr Artsikhovich
 * @version 1.0
 * @see AccountService
 */
public class AccountServiceImpl implements AccountService {

    private static final Logger logger = LogManager.getLogger();

    private final AccountDao accountDao;

    public AccountServiceImpl(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    /**
     * Registers a new account
     *
     * @param account {@link Account} the bank account
     * @return the boolean
     * @throws ServiceException the ServiceException exception
     */
    public boolean addAccount(Account account) throws ServiceException {
        try {
            if (accountDao.getByCodeIBAN(account.getCodeIBAN()).isPresent()) {
                throw new ServiceException("The account has already registered in the database - "
                        + account.getCodeIBAN());
            } else {
                return accountDao.add(account);
            }
        } catch (CleverDatabaseException e) {
            throw new ServiceException("account registration error", e);
        }
    }

    /**
     * Finds all accounts
     *
     * @return {@link List} of {@link Account} the list of found accounts
     * @throws ServiceException the ServiceException exception
     */
    public List<Account> findAllAccounts() throws ServiceException {
        try {
            return accountDao.getAll();
        } catch (CleverDatabaseException e) {
            throw new ServiceException("An error occurred while retrieving all accounts", e);
        }
    }

    /**
     * Finds the account by his IBAN number
     *
     * @param codeIBAN {@link String} the user's passport id number
     * @return {@link Optional} of {@link Account} the optional of found account
     * @throws ServiceException the ServiceException exception
     */
    public Optional<Account> findByCodeIBAN(String codeIBAN) throws ServiceException {
        try {
            return accountDao.getByCodeIBAN(codeIBAN);
        } catch (CleverDatabaseException e) {
            throw new ServiceException("An error occurred while retrieving the account by his lastname", e);
        }
    }

    /**
     * Finds all accounts of the user
     *
     * @param user {@link User} the owner of the account
     * @return {@link List} of {@link Account} the list of found accounts of the user
     * @throws ServiceException the ServiceException exception
     */
    public List<Account> findAllForUser(User user) throws ServiceException {
        try {
            return accountDao.getAllForUser(user);
        } catch (CleverDatabaseException e) {
            throw new ServiceException("An error occurred while retrieving the accounts by its owner", e);
        }
    }

    /**
     * Finds all accounts of the bank
     *
     * @param bank {@link Bank} the bank which has a list of accounts
     * @return {@link List} of {@link Account} the list of found accounts of the bank
     * @throws ServiceException the ServiceException exception
     */
    public List<Account> findAllForBank(Bank bank) throws ServiceException {
        try {
            return accountDao.getAllForBank(bank);
        } catch (CleverDatabaseException e) {
            throw new ServiceException("An error occurred while retrieving the accounts by its bank", e);
        }
    }

    /**
     * Changes the account data
     *
     * @param account {@link Account} the bank account
     * @return the boolean is the account updated
     * @throws ServiceException the ServiceException exception
     */
    public boolean updateAccount(Account account) throws ServiceException {
        try {
            return accountDao.update(account);
        } catch (CleverDatabaseException e) {
            throw new ServiceException("An error occurred while updating the account", e);
        }
    }

    /**
     * Removes the account by his IBAN number
     *
     * @param codeIBAN {@link String} the IBAN code of the account
     * @return the boolean is the account deleted
     * @throws ServiceException the ServiceException exception
     */
    public boolean deleteAccount(String codeIBAN) throws ServiceException {
        try {
            return accountDao.delete(codeIBAN);
        } catch (CleverDatabaseException e) {
            throw new ServiceException("An error occurred while deleting the account", e);
        }
    }
}
