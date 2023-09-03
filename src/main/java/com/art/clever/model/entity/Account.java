package com.art.clever.model.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Bean class of model layer represents the bank account of the user
 *
 * @author Aliaksandr Artsikhovich
 * @version 1.0
 */
@Data
public class Account {
    /**
     * Unique identification {@code String} value for the account, also used during authorization
     * process. It is the IBAN code for this account.
     */
    private final String numberIBAN;
    /**
     * Bank {@link Bank} of the account
     */
    private final Bank bank;
    /**
     * User {@link User} who is the owner of the account
     */
    private final User user;
    /**
     * Currency {@link Currency} of the account
     */
    private Currency currency;
    /**
     * Date {@link LocalDateTime} of creating the account
     */
    private LocalDateTime createdAt;
    /**
     * Balance {@link BigDecimal} of the account
     */
    private BigDecimal balance;
}
