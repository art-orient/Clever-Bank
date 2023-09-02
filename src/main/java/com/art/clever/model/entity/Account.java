package com.art.clever.model.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Account {
    private final long userId;
    private String number;
    private BigDecimal balance;
    private LocalDateTime createdAt;
    private Currency currency;
}
