package com.art.clever.model.entity;

import lombok.Data;

/**
 * Bean class of model layer represents the user
 *
 * @author Aliaksandr Artsikhovich
 * @version 1.0
 */
@Data
public class User {
    /**
     * Unique identification {@code String} value for user's account, also used during authorization
     * process. It is a value from the user's passport.
     */
    private String passportId;
    /**
     * Last name {@code String} of the user
     */
    private String lastName;
    /**
     * First name {@code String} of the user
     */
    private String firstName;
    /**
     * Surname {@code String} of the user if exists
     */
    private String surName;
}
