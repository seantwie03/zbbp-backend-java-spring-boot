package me.seantwiehaus.zbbp.domain;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CategoryTest {
    @Nested
    class withTransactions {
        private static Category category;

        @BeforeAll
        static void setup() {
            List<Transaction> transactions = List.of(
                    new Transaction(null,
                            1L,
                            new Money(10.50),
                            LocalDate.now(),
                            "Transaction 1",
                            null),
                    new Transaction(null,
                            1L,
                            new Money(11.00),
                            LocalDate.now(),
                            "Transaction 2",
                            null),
                    new Transaction(null,
                            1L,
                            new Money(4.00),
                            LocalDate.now(),
                            "Transaction 3",
                            null)
            );
            category = new Category(
                    null,
                    1L,
                    "Groceries",
                    1L,
                    new Money(50.0),
                    new BudgetMonth(),
                    transactions);
        }

        @Test
        void shouldCalculateCorrectTotal() {
            assertEquals(new Money(25.50), category.getSpentAmount());
        }

        @Test
        void shouldCalculateCorrectPercentageSpent() {
            assertEquals(51.0, category.getSpentPercent());
        }

        @Test
        void shouldCalculateCorrectAmountRemaining() {
            assertEquals(new Money(24.50), category.getRemainingAmount());
        }

        @Test
        void shouldCalculateCorrectPercentageRemaining() {
            assertEquals(49.0, category.getRemainingPercent());
        }
    }

    @Nested
    class withoutTransactions {
        private static Category category;

        @BeforeAll
        static void setup() {
            category = new Category(
                    null,
                    null,
                    "Restaurants",
                    1L,
                    new Money(50.0),
                    new BudgetMonth(),
                    null
            );
        }

        @Test
        void shouldCalculateCorrectTotal() {
            assertEquals(new Money(0), category.getSpentAmount());
        }

        @Test
        void shouldCalculateCorrectPercentageSpent() {
            assertEquals(0.0, category.getSpentPercent());
        }

        @Test
        void shouldCalculateCorrectAmountRemaining() {
            assertEquals(new Money(50.0), category.getRemainingAmount());
        }

        @Test
        void shouldCalculateCorrectPercentageRemaining() {
            assertEquals(100.0, category.getRemainingPercent());
        }

    }
}
