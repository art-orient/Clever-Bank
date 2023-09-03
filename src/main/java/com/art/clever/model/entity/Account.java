package com.art.clever.model.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Bean class of model layer represents the bank account of the user
 *
 * @author Aliaksandr Artsikhovich
 * @version 1.0
 */
@Data
public class Account {
    /**
     * Unique identification {@code String} value for the account.
     * It is the IBAN code for this account.
     */
    private String codeIBAN;
    /**
     * Bank {@link Bank} of the account
     */
    private Bank bank;
    /**
     * User {@link User} who is the owner of the account
     */
    private User user;
    /**
     * Currency {@link Currency} of the account
     */
    private Currency currency;
    /**
     * Date and time {@link Instant} of creating the account
     */
    private Instant createdAt;
    /**
     * Balance {@link BigDecimal} of the account
     */
    private BigDecimal balance;
}
