package com.art.clever.model.dao;

import com.art.clever.exception.CleverDatabaseException;
import com.art.clever.model.entity.Bank;

import java.util.List;
import java.util.Optional;

/**
 * The {@code BankDao} interface for working with database table banks
 *
 * @author Aliaksandr Artsikhovich
 * @version 1.0
 */
public interface BankDao {

    /**
     * Saves the bank
     *
     * @param bank {@link Bank} the bank
     * @return the boolean is the bank saved
     * @throws CleverDatabaseException the CleverDatabaseException exception
     */
    boolean add(Bank bank) throws CleverDatabaseException;

    /**
     * Finds all banks
     *
     * @return {@link List} of {@link Bank} the list of found banks
     * @throws CleverDatabaseException the CleverDatabaseException exception
     */
    List<Bank> getAll() throws CleverDatabaseException;

    /**
     * Finds the bank by his BIC code
     *
     * @param bicCode {@link String} the BIC code of the bank
     * @return {@link Optional} of {@link Bank} the optional of the found bank
     * @throws CleverDatabaseException the CleverDatabaseException exception
     */
    Optional<Bank> getByBicCode(String bicCode) throws CleverDatabaseException;

    /**
     * Finds the bank by his name
     *
     * @param bankName {@link String} the name of the bank
     * @return {@link Optional} of {@link Bank} the optional of the found bank
     * @throws CleverDatabaseException the CleverDatabaseException exception
     */
    Optional<Bank> getByBankName(String bankName) throws CleverDatabaseException;

    /**
     * Changes bank name
     *
     * @param bank {@link Bank} the bank
     * @return the boolean is the bank updated
     * @throws CleverDatabaseException the CleverDatabaseException exception
     */
    boolean update(Bank bank) throws CleverDatabaseException;

    /**
     * Removes the bank by his BIC code
     *
     * @param bicCode {@link String} the BIC code of the bank
     * @return the boolean is the bank deleted
     * @throws CleverDatabaseException the CleverDatabaseException exception
     */
    boolean delete(String bicCode) throws CleverDatabaseException;
}
