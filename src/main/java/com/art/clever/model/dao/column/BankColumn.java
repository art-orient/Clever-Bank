package com.art.clever.model.dao.column;

/**
 * The {@code BanksColumn} class of constants for working with database table banks
 *
 * @author Aliaksandr Artsikhovich
 * @version 1.0
 */
public class BankColumn {
    public static final String BIC_CODE = "bic_code";
    public static final String BANK_NAME = "bank_name";

    public static final int BIC_CODE_INDEX = 1;
    public static final int BANK_NAME_INDEX = 2;

    private BankColumn() {
    }
}
