package me.seantwiehaus.zbbp.domain;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CategoryTest {

    private static Category category;

    @BeforeAll
    static void setup() {
        List<Transaction> transactions = List.of(
                new Transaction(null,
                        1L,
                        BigDecimal.valueOf(10.50),
                        LocalDate.now(),
                        "Transaction 1",
                        null),
                new Transaction(null,
                        1L,
                        BigDecimal.valueOf(11.00),
                        LocalDate.now(),
                        "Transaction 2",
                        null),
                new Transaction(null,
                        1L,
                        BigDecimal.valueOf(4.00),
                        LocalDate.now(),
                        "Transaction 3",
                        null)
        );
        category = new Category(null,
                1L,
                "Groceries",
                BigDecimal.valueOf(50),
                new BudgetMonth(),
                transactions);
    }

    @Test
    void shouldCalculateCorrectTotal() {
        assertEquals(BigDecimal.valueOf(25.50), category.getSpentAmount());
    }

    @Test
    void shouldCalculateCorrectPercentageSpent() {
        assertEquals(51.0, category.getSpentPercent());
    }

    @Test
    void shouldCalculateCorrectAmountRemaining() {
        assertEquals(BigDecimal.valueOf(24.50), category.getRemainingAmount());
    }

    @Test
    void shouldCalculateCorrectPercentageRemaining() {
        assertEquals(49.0, category.getRemainingPercent());
    }
}
