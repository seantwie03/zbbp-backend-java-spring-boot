package me.seantwiehaus.zbbp.dto;

import lombok.Getter;
import me.seantwiehaus.zbbp.domain.Transaction;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Getter
public class TransactionDto extends BaseDto {
    private final Long id;
    @NotNull
    private final BigDecimal amount;
    @NotNull
    private final LocalDate date;
    @NotBlank
    private final String description;

    public TransactionDto(BigDecimal amount,
                          LocalDate date,
                          String description) {
        this(null, null, null, null, amount, date, description);
    }

    public TransactionDto(Integer version,
                          Instant createdAt,
                          Instant modifiedAt,
                          Long id,
                          BigDecimal amount,
                          LocalDate date,
                          String description) {
        super(version, createdAt, modifiedAt);
        this.id = id;
        this.amount = amount;
        this.date = date;
        this.description = description;
    }

    public TransactionDto(Transaction transaction) {
        super(transaction.getVersion(), transaction.getCreatedAt(), transaction.getLastModifiedAt());
        this.id = transaction.getId();
        this.amount = transaction.getAmount();
        this.date = transaction.getDate();
        this.description = transaction.getDescription();
    }
}
