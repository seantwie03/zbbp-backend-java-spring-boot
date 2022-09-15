package me.seantwiehaus.zbbp.dto;

import lombok.Getter;
import me.seantwiehaus.zbbp.domain.BudgetMonth;
import me.seantwiehaus.zbbp.domain.Category;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Getter
public class CategoryDto extends BaseDto {
    private final Long id;
    @NotBlank
    private final String name;
    @NotNull
    private final BigDecimal plannedAmount;
    /**
     * Day of Month will be set to the 1st
     */
    @NotNull
    private final LocalDate budgetDate;
    private final BigDecimal transactionTotal;
    /**
     * Unmodifiable List
     */
    @NotNull
    private final List<TransactionDto> transactionDtos;

    public CategoryDto(Instant modifiedAt,
                       Long id,
                       String name,
                       BigDecimal plannedAmount,
                       LocalDate budgetDate,
                       BigDecimal transactionTotal,
                       List<TransactionDto> transactionDtos) {
        super(modifiedAt);
        this.id = id;
        this.name = name;
        this.plannedAmount = plannedAmount;
        this.budgetDate = new BudgetMonth(budgetDate).asLocalDate();
        this.transactionTotal = transactionTotal;
        this.transactionDtos = Collections.unmodifiableList(transactionDtos);
    }

    public CategoryDto(Category category) {
        super(category.getLastModifiedAt());
        this.id = category.getId();
        this.name = category.getName();
        this.plannedAmount = category.getPlannedAmount();
        this.budgetDate = category.getBudgetMonth().asLocalDate();
        this.transactionTotal = category.calculateTransactionAmountTotal();
        this.transactionDtos = category.getTransactions()
                .stream()
                .map(TransactionDto::new)
                .toList();
    }
}
