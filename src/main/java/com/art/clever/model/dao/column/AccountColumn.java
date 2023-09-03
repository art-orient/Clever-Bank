package com.art.clever.model.dao.column;

/**
 * The {@code UsersColumn} class of constants for working with database table accounts
 *
 * @author Aliaksandr Artsikhovich
 * @version 1.0
 */
public class AccountColumn {
    public static final String CODE_IBAN = "code_iban";
    public static final String BANK_BIC_CODE = "bank_bic_code";
    public static final String USER_ID = "user_passport_id";
    public static final String CURRENCY = "currency";
    public static final String CREATED_AT = "created_at";
    public static final String BALANCE = "balance";

    public static final int CODE_IBAN_INDEX = 1;
    public static final int BANK_BIC_CODE_INDEX = 2;
    public static final int USER_ID_INDEX = 3;
    public static final int CURRENCY_INDEX = 4;
    public static final int CREATED_AT_INDEX = 5;
    public static final int BALANCE_INDEX = 6;

    private AccountColumn() {
    }
}
