package me.seantwiehaus.zbbp.domain;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collections;
import java.util.List;

@Getter
public class Category extends BaseDomain {
    private final Long id;
    private final String name;
    private final BigDecimal plannedAmount;
    private final BudgetMonth budgetMonth;
    private final BigDecimal transactionTotal;
    /**
     * Unmodifiable List
     */
    private final List<Transaction> transactions;

    public Category(Instant lastModifiedAt,
                    Long id,
                    String name,
                    BigDecimal plannedAmount,
                    BudgetMonth budgetMonth,
                    List<Transaction> transactions) {
        super(lastModifiedAt);
        this.id = id;
        this.name = name;
        this.plannedAmount = plannedAmount;
        this.budgetMonth = budgetMonth;
        this.transactions = Collections.unmodifiableList(transactions);
        this.transactionTotal = calculateTransactionAmountTotal(transactions);
    }

    public BigDecimal calculateTransactionAmountTotal(List<Transaction> transactions) {
        return transactions
                .stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.valueOf(0), BigDecimal::add);
    }
}
