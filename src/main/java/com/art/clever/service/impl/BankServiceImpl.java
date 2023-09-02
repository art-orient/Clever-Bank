package com.art.clever.service.impl;

import com.art.clever.exception.CleverDatabaseException;
import com.art.clever.exception.ServiceException;
import com.art.clever.model.dao.BankDao;
import com.art.clever.model.entity.Bank;
import com.art.clever.service.BankService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

/**
 * The {@code BankServiceImpl} class represents bank service implementation
 *
 * @author Aliaksandr Artsikhovich
 * @version 1.0
 * @see BankService
 */
public class BankServiceImpl implements BankService {

    private static final Logger logger = LogManager.getLogger();

    private final BankDao bankDao;

    public BankServiceImpl(BankDao bankDao) {
        this.bankDao = bankDao;
    }

    /**
     * Registers a new bank
     *
     * @param bank {@link Bank} the bank
     * @return the boolean is the bank saved
     * @throws ServiceException the ServiceException exception
     */
    @Override
    public boolean addBank(Bank bank) throws ServiceException {
        try {
            if (bankDao.getByBicCode(bank.getCodeBic()).isPresent() ||
                    bankDao.getByBankName(bank.getName()).isPresent()) {
                throw new ServiceException("The bank has already registered in the database - "
                        + bank.getCodeBic() + ", " + bank.getName());
            } else {
                return bankDao.add(bank);
            }
        } catch (CleverDatabaseException e) {
            throw new ServiceException("bank registration error", e);
        }
    }

    /**
     * Finds all banks
     *
     * @return {@link List} of {@link Bank} the list of found banks
     * @throws ServiceException the ServiceException exception
     */
    @Override
    public List<Bank> findAllBanks() throws ServiceException {
        try {
            return bankDao.getAll();
        } catch (CleverDatabaseException e) {
            throw new ServiceException("An error occurred while retrieving banks from the database", e);
        }
    }

    /**
     * Finds the bank by his BIC code
     *
     * @param bicCode {@link String} the BIC code of the bank
     * @return {@link Optional} of {@link Bank} the optional of the found bank
     * @throws ServiceException the ServiceException exception
     */
    @Override
    public Optional<Bank> findBankByBicCode(String bicCode) throws ServiceException {
        try {
            return bankDao.getByBicCode(bicCode);
        } catch (CleverDatabaseException e) {
            throw new ServiceException("An error occurred while retrieving the bank by his BIC code = " +
                    bicCode + " from the database", e);
        }
    }

    /**
     * Finds the bank by his name
     *
     * @param bankName {@link String} the name of the bank
     * @return {@link Optional} of {@link Bank} the optional of the found bank
     * @throws ServiceException the ServiceException exception
     */
    @Override
    public Optional<Bank> findBankByBankName(String bankName) throws ServiceException {
        try {
            return bankDao.getByBankName(bankName);
        } catch (CleverDatabaseException e) {
            throw new ServiceException("An error occurred while retrieving the bank by his name" +
                    " from the database", e);
        }
    }

    /**
     * Updates the bank
     *
     * @param bank {@link Bank} the bank
     * @return the boolean is the bank updated
     * @throws ServiceException the ServiceException exception
     */
    @Override
    public boolean updateBank(Bank bank) throws ServiceException {
        try {
            return bankDao.update(bank);
        } catch (CleverDatabaseException e) {
            throw new ServiceException("An error occurred while updating the bank", e);
        }
    }

    /**
     * Removes the bank by his BIC code
     *
     * @param bicCode {@link String} the BIC code of the bank
     * @return the boolean is the bank deleted
     * @throws ServiceException the ServiceException exception
     */
    @Override
    public boolean deleteBank(String bicCode) throws ServiceException {
        try {
            return bankDao.delete(bicCode);
        } catch (CleverDatabaseException e) {
            throw new ServiceException("An error occurred while deleting the bank", e);
        }
    }
}
