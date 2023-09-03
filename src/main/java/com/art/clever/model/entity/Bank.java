package com.art.clever.model.entity;

import lombok.Data;

/**
 * Bean class of model layer represents the bank
 *
 * @author Aliaksandr Artsikhovich
 * @version 1.0
 */
@Data
public class Bank {
    /**
     * Unique identification {@code String} value for the bank.
     * It is the BIC code for this bank.
     */
    private String codeBic;
    /**
     * Name {@code String} of the bank
     */
    private String name;
}
