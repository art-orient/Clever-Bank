package com.art.clever.model.dao.column;

/**
 * The {@code TransactionColumn} class of constants for working with database table transactions
 *
 * @author Aliaksandr Artsikhovich
 * @version 1.0
 */
public class TransactionColumn {
    public static final String ACTION_ID = "action_id";
    public static final String COMPLETED_AT = "completed_at";
    public static final String ACTION_TYPE = "type";
    public static final String FROM_IBAN = "from_iban";
    public static final String TO_IBAN = "to_iban";
    public static final String CURRENCY = "currency";
    public static final String AMOUNT = "amount";

    public static final int ACTION_ID_INDEX = 1;
    public static final int COMPLETED_AT_INDEX = 2;
    public static final int ACTION_TYPE_INDEX = 3;
    public static final int FROM_IBAN_INDEX = 4;
    public static final int TO_IBAN_INDEX = 5;
    public static final int CURRENCY_INDEX = 6;
    public static final int AMOUNT_INDEX = 7;

    private TransactionColumn() {
    }
}
