package me.seantwiehaus.zbbp.dto;

import me.seantwiehaus.zbbp.domain.Transaction;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public record TransactionDto(
        @NotNull Long id,
        @NotNull BigDecimal amount,
        @NotNull LocalDate date,
        @NotBlank String description
) {
    public TransactionDto(Transaction transaction) {
        this(transaction.getId(),
                transaction.getAmount(),
                transaction.getDate(),
                transaction.getDescription());
    }
}
