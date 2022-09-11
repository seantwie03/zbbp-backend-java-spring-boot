package me.seantwiehaus.zbbp.domain;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
public class Category {
    private final Long id;
    private final String name;
    private final BigDecimal plannedAmount;
    private final LocalDate budgetDate;
    private final List<Transaction> transactions;

    public Category(Long id, String name, BigDecimal plannedAmount, LocalDate budgetDate, List<Transaction> transactions) {
        this.id = id;
        this.name = name;
        this.plannedAmount = plannedAmount;
        this.budgetDate = budgetDate;
        this.transactions = transactions;
    }

    public BigDecimal calculateTransactionAmountTotal() {
        return transactions.stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.valueOf(0.00), BigDecimal::add);
    }
}
