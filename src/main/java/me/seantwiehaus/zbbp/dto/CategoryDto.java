package me.seantwiehaus.zbbp.dto;

import me.seantwiehaus.zbbp.domain.Category;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public record CategoryDto(
        @NotNull Long id,
        @NotBlank String name,
        @NotNull BigDecimal plannedAmount,
        @NotNull LocalDate budgetDate,
        @NotNull BigDecimal transactionTotal,
        @NotNull List<TransactionDto> transactionDtos
) {
    public CategoryDto(Category category) {
        this(category.getId(),
                category.getName(),
                category.getPlannedAmount(),
                category.getBudgetDate(),
                category.calculateTransactionAmountTotal(),
                new ArrayList<>(
                        category.getTransactions().stream()
                                .map(TransactionDto::new)
                                .toList()));
    }
}
