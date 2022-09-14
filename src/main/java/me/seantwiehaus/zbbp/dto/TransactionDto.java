package me.seantwiehaus.zbbp.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.ToString;
import me.seantwiehaus.zbbp.domain.Transaction;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Getter
@ToString
public class TransactionDto extends BaseDto {
    private final Long id;
    @NotNull
    private final BigDecimal amount;
    @NotNull
    private final LocalDate date;
    @NotBlank
    private final String description;

    @JsonCreator
    public TransactionDto(BigDecimal amount,
                          LocalDate date,
                          String description) {
        this(null, null, amount, date, description);
    }

    public TransactionDto(Instant modifiedAt,
                          Long id,
                          BigDecimal amount,
                          LocalDate date,
                          String description) {
        super(modifiedAt);
        this.id = id;
        this.amount = amount;
        this.date = date;
        this.description = description;
    }

    public TransactionDto(Transaction transaction) {
        super(transaction.getLastModifiedAt());
        this.id = transaction.getId();
        this.amount = transaction.getAmount();
        this.date = transaction.getDate();
        this.description = transaction.getDescription();
    }

    public Transaction convertToTransaction() {
        return new Transaction(
                modifiedAt,
                id,
                amount,
                date,
                description);
    }
}
