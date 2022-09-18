package me.seantwiehaus.zbbp.domain;

import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Getter
@ToString
public class Transaction extends BaseDomain {
    private final Long id;
    private final BigDecimal amount;
    private final LocalDate date;
    private final String description;
    private final Long categoryId;

    public Transaction(Instant lastModifiedAt,
                       Long id,
                       BigDecimal amount,
                       LocalDate date,
                       String description,
                       Long categoryId) {
        super(lastModifiedAt);
        this.id = id;
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.categoryId = categoryId;
    }
}
