package com.art.clever.model.dao.column;

/**
 * The {@code UsersColumn} class of constants for working with database table users
 *
 * @author Aliaksandr Artsikhovich
 * @version 1.0
 */
public class UserColumn {
    public static final String PASSPORT_ID = "passport";
    public static final String LASTNAME = "lastname";
    public static final String FIRSTNAME = "firstname";
    public static final String SURNAME = "surname";

    public static final int PASSPORT_ID_INDEX = 1;
    public static final int LASTNAME_INDEX = 2;
    public static final int FIRSTNAME_INDEX = 3;
    public static final int SURNAME_INDEX = 4;

    private UserColumn() {
    }
}
