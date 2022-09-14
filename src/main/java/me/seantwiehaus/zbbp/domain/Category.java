package me.seantwiehaus.zbbp.domain;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Getter
@SuppressWarnings("java:S107")
public class Category extends BaseDomain {
    private final Long id;
    private final String name;
    private final BigDecimal plannedAmount;
    private final BudgetMonth budgetMonth;
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
        this.transactions = transactions;
    }

    public BigDecimal calculateTransactionAmountTotal() {
        return transactions
                .stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.valueOf(0), BigDecimal::add);
    }
}
