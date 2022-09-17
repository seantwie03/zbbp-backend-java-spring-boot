package me.seantwiehaus.zbbp.domain;

import lombok.Getter;

import java.time.LocalDate;

/**
 * A BudgetMonth is a Year/Month combination. For compatability with database types and REST API clients this app will
 * use a standard ISO 8601 Date format; however, in the business logic this custom BudgetMonth class will be used to
 * ensure that if a Day portion of the date is needed, it will always be set to the 1st.
 */
@Getter
public class BudgetMonth {
    private final LocalDate date;

    public BudgetMonth() {
        this(LocalDate.now());
    }

    public BudgetMonth(LocalDate date) {
        this.date = date.withDayOfMonth(1);
    }

    public LocalDate asLocalDate() {
        return date;
    }
}
