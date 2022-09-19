package me.seantwiehaus.zbbp.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class BudgetMonthTest {
    @Test
    void shouldBeEqualWhenCreatedUsingDifferentConstructors() {
        BudgetMonth b1 = new BudgetMonth();
        BudgetMonth b2 = new BudgetMonth(LocalDate.now());

        assertEquals(b1, b2);
    }

    @Test
    void shouldBeEqualWhenSameMonthDifferentDay() {
        BudgetMonth b1 = new BudgetMonth(LocalDate.of(2025, 2, 1));
        BudgetMonth b2 = new BudgetMonth(LocalDate.of(2025, 2, 2));

        assertEquals(b1, b2);
    }

    @Test
    void shouldNotBeEqualWhenDifferentYears() {
        BudgetMonth b1 = new BudgetMonth(LocalDate.now());
        BudgetMonth b2 = new BudgetMonth(LocalDate.now().plusYears(1));

        assertNotEquals(b1, b2);
    }

    @Test
    void shouldNotBeEqualWhenDifferentMonths() {
        BudgetMonth b1 = new BudgetMonth(LocalDate.now());
        BudgetMonth b2 = new BudgetMonth(LocalDate.now().plusMonths(1));

        assertNotEquals(b1, b2);
    }
}
