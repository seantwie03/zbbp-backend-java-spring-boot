package me.seantwiehaus.zbbp.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class Transaction {
    private final Long id;
    private final BigDecimal amount;
    private final LocalDate date;
    private final String description;
}
