package com.art.clever.model.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Account {
    private final Bank bank;
    private final String userPassportId;
    private final String number;
    private Currency currency;
    private LocalDateTime createdAt;
    private BigDecimal balance;
}
