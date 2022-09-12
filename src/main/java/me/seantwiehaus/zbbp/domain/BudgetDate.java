package me.seantwiehaus.zbbp.domain;

import java.time.LocalDate;

/**
 * A BudgetDate is a Year/Month combination. For compatability with database types and REST API clients this app will
 * use a standard ISO 8601 Date format; however, in the business logic this custom BudgetDate class will be used to
 * ensure that if a Day portion of the date is needed, it will always be set to the 1st.
 */
public class BudgetDate {
    private final LocalDate date;

    public static BudgetDate from(LocalDate date) {
        return new BudgetDate(date.withDayOfMonth(1));
    }

    public BudgetDate(LocalDate date) {
        this.date = date.withDayOfMonth(1);
    }

    public LocalDate asLocalDate() {
        return date;
    }
}
