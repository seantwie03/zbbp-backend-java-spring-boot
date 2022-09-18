package me.seantwiehaus.zbbp.dto.response;

import lombok.Getter;
import me.seantwiehaus.zbbp.domain.BudgetMonth;
import me.seantwiehaus.zbbp.domain.Category;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Getter
@SuppressWarnings("java:S107")
public class CategoryResponse extends BaseResponse {
    private final Long id;
    private final String name;
    private final Long categoryGroupId;
    private final BigDecimal plannedAmount;
    /**
     * Day of Month will be set to the 1st
     */
    private final LocalDate budgetDate;
    private final BigDecimal spentAmount;
    private final Double spentPercent;
    private final BigDecimal remainingAmount;
    private final Double remainingPercent;
    /**
     * Unmodifiable List
     */
    private final List<TransactionResponse> transactionResponseDtos;

    public CategoryResponse(Instant modifiedAt,
                            Long id,
                            String name,
                            Long categoryGroupId,
                            BigDecimal plannedAmount,
                            LocalDate budgetDate,
                            BigDecimal spentAmount,
                            Double spentPercent,
                            BigDecimal remainingAmount,
                            Double remainingPercent,
                            List<TransactionResponse> transactionResponseDtos) {
        super(modifiedAt);
        this.id = id;
        this.name = name;
        this.categoryGroupId = categoryGroupId;
        this.plannedAmount = plannedAmount;
        this.budgetDate = new BudgetMonth(budgetDate).asLocalDate();
        this.spentAmount = spentAmount;
        this.spentPercent = spentPercent;
        this.remainingAmount = remainingAmount;
        this.remainingPercent = remainingPercent;
        this.transactionResponseDtos = Collections.unmodifiableList(transactionResponseDtos);
    }

    public CategoryResponse(Category category) {
        super(category.getLastModifiedAt());
        this.id = category.getId();
        this.name = category.getName();
        this.categoryGroupId = category.getCategoryGroupId();
        this.plannedAmount = category.getPlannedAmount();
        this.budgetDate = category.getBudgetMonth().asLocalDate();
        this.spentAmount = category.getSpentAmount();
        this.spentPercent = category.getSpentPercent();
        this.remainingAmount = category.getRemainingAmount();
        this.remainingPercent = category.getRemainingPercent();
        this.transactionResponseDtos = category.getTransactions()
                .stream()
                .map(TransactionResponse::new)
                .toList();
    }

    public Category convertToCategory() {
        return new Category(lastModifiedAt,
                id,
                name,
                categoryGroupId,
                plannedAmount,
                new BudgetMonth(budgetDate),
                transactionResponseDtos.stream()
                        .map(TransactionResponse::convertToTransaction)
                        .toList()
        );
    }
}
