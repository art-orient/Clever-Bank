package com.art.clever.model.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Transaction {
    private LocalDateTime time;
    private long fromAccountId;
    private long toAccountId;
    private BigDecimal amount;
    private Currency currency;
}
