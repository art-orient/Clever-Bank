package com.art.clever.service;

import com.art.clever.exception.CleverDatabaseException;
import com.art.clever.exception.ServiceException;
import com.art.clever.model.entity.Bank;
import com.art.clever.model.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * The {@code BankService} interface represents bank service
 *
 * @author Aliaksandr Artsikhovich
 * @version 1.0
 */
public interface BankService {

    /**
     * Registers a new bank
     *
     * @param bank {@link Bank} the bank
     * @return the boolean is the bank saved
     * @throws ServiceException the ServiceException exception
     */
    boolean addBank(Bank bank) throws ServiceException;

    /**
     * Finds all banks
     *
     * @return {@link List} of {@link Bank} the list of found banks
     * @throws ServiceException the ServiceException exception
     */
    List<Bank> findAllBanks() throws ServiceException;

    /**
     * Finds the bank by his BIC code
     *
     * @param bicCode {@link String} the BIC code of the bank
     * @return {@link Optional} of {@link Bank} the optional of the found bank
     * @throws ServiceException the ServiceException exception
     */
    Optional<Bank> findBankByBicCode(String bicCode) throws ServiceException;

    /**
     * Finds the bank by his name
     *
     * @param bankName {@link String} the name of the bank
     * @return {@link Optional} of {@link Bank} the optional of the found bank
     * @throws ServiceException the ServiceException exception
     */
    Optional<Bank> findBankByBankName(String bankName) throws ServiceException;

    /**
     * Updaes the bank
     *
     * @param bank {@link Bank} the bank
     * @return the boolean is the bank updated
     * @throws ServiceException the ServiceException exception
     */
    boolean updateBank(Bank bank) throws ServiceException;

    /**
     * Removes the bank by his BIC code
     *
     * @param bicCode {@link String} the BIC code of the bank
     * @return the boolean is the bank deleted
     * @throws ServiceException the ServiceException exception
     */
    boolean deleteBank(String bicCode) throws ServiceException;
}
