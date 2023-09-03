package com.art.clever.model.entity;

/**
 * Enum represents the type of the transaction
 *
 * @author Aliaksandr Artsikhovich
 * @version 1.0
 */
public enum TransactionType {

    ADDING ("Пополнение"),
    TRANSFER ("Перевод"),
    WITHDRAWAL ("Снятие средств");

    private String title;

    TransactionType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
