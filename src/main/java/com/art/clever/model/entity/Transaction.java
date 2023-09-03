package com.art.clever.model.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;


/**
 * Bean class of model layer represents the transaction
 *
 * @author Aliaksandr Artsikhovich
 * @version 1.0
 */
@Data
public class Transaction {
    /**
     * Unique identification {@code long} value for the transaction.
     * It gets a value from the database during saving the transaction.
     */
    private long actionId;
    /**
     * Date and time {@link Instant} of the transaction
     */
    private Instant time;
    /**
     * Type {@link TransactionType} of the transaction
     */
    private TransactionType transactionType;
    /**
     * Account IBAN code {@link TransactionType} which used for sending money
     */
    private String fromAccountId;
    /**
     * Account IBAN code {@link TransactionType} which used for getting money
     */
    private String toAccountId;
    /**
     * Currency {@link TransactionType} of the transfer
     */
    private Currency currency;
    /**
     * Amount {@link TransactionType} of the transfer
     */
    private BigDecimal amount;
}
