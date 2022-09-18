package me.seantwiehaus.zbbp.domain;

import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.Collections;
import java.util.List;

@Getter
@ToString
public class Category extends BaseDomain {
    private final Long id;
    private final String name;
    private final Long categoryGroupId;
    private final BigDecimal plannedAmount;
    private final BudgetMonth budgetMonth;
    private final BigDecimal spentAmount;
    private final Double spentPercent;
    private final BigDecimal remainingAmount;
    private final Double remainingPercent;
    /**
     * Unmodifiable List
     */
    private final List<Transaction> transactions;

    public Category(Instant lastModifiedAt,
                    Long id,
                    String name,
                    Long categoryGroupId,
                    BigDecimal plannedAmount,
                    BudgetMonth budgetMonth,
                    List<Transaction> transactions) {
        super(lastModifiedAt);
        this.id = id;
        this.name = name;
        this.categoryGroupId = categoryGroupId;
        this.plannedAmount = plannedAmount;
        this.budgetMonth = budgetMonth;
        this.transactions = Collections.unmodifiableList(transactions);
        this.spentAmount = calculateAmountSpent();
        this.spentPercent = calculatePercentageSpentOfPlannedAmount();
        this.remainingAmount = calculateAmountRemainingOfPlannedAmount();
        this.remainingPercent = calculatePercentageRemainingOfPlannedAmount();
    }

    private BigDecimal calculateAmountSpent() {
        return transactions
                .stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.valueOf(0), BigDecimal::add);
    }

    private Double calculatePercentageSpentOfPlannedAmount() {
        return spentAmount
                .multiply(BigDecimal.valueOf(100))
                .divide(plannedAmount, RoundingMode.DOWN)
                .doubleValue();
    }

    private BigDecimal calculateAmountRemainingOfPlannedAmount() {
        return plannedAmount.subtract(spentAmount);
    }

    private Double calculatePercentageRemainingOfPlannedAmount() {
        return remainingAmount
                .multiply(BigDecimal.valueOf(100))
                .divide(plannedAmount, RoundingMode.DOWN)
                .doubleValue();
    }

}
