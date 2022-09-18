package me.seantwiehaus.zbbp.dto.request;

import lombok.Getter;
import lombok.ToString;
import me.seantwiehaus.zbbp.domain.Transaction;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Getter
@ToString
public class TransactionRequest {
    @NotNull
    @Min(0)
    private final Double amount;
    @NotNull
    private final LocalDate date;
    @NotBlank
    private final String description;
    private final Long categoryId;

    public TransactionRequest(Double amount,
                              LocalDate date,
                              String description) {
        this(amount, date, description, null);
    }

    public TransactionRequest(Double amount,
                              LocalDate date,
                              String description,
                              Long categoryId) {
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.categoryId = categoryId;
    }

    public Transaction convertToTransaction() {
        return new Transaction(
                null,
                null,
                BigDecimal.valueOf(amount),
                date,
                description,
                categoryId);
    }

    public Transaction convertToTransaction(Long id, Instant lastModifiedAt) {
        return new Transaction(
                lastModifiedAt,
                id,
                BigDecimal.valueOf(amount),
                date,
                description,
                categoryId);
    }
}
