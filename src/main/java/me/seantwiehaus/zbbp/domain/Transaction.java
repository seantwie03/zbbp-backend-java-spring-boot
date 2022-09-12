package me.seantwiehaus.zbbp.domain;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Getter
public class Transaction extends BaseDomain {
    private final Long id;
    private final BigDecimal amount;
    private final LocalDate date;
    private final String description;

    public Transaction(int version,
                       Instant createdAt,
                       Instant lastModifiedAt,
                       Long id,
                       BigDecimal amount,
                       LocalDate date,
                       String description) {
        super(version, createdAt, lastModifiedAt);
        this.id = id;
        this.amount = amount;
        this.date = date;
        this.description = description;
    }
}
