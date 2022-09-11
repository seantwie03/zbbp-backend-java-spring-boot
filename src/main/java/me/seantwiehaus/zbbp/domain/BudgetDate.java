package me.seantwiehaus.zbbp.domain;

import java.time.LocalDate;
import java.time.YearMonth;

/**
 * A BudgetDate is a Year/Month combination. For compatability with database types and REST API clients this app will
 * use a standard ISO 8601 Date format; however, in the business logic this custom BudgetDate class will be used to
 * ensure that if a Day portion of the date is needed, it will always be set to the 1st.
 */
public class BudgetDate {
    private final LocalDate date;

    public static BudgetDate from(LocalDate localDate) {
        return new BudgetDate(localDate.withDayOfMonth(1));
    }

    public static BudgetDate from(YearMonth yearMonth) {
        return new BudgetDate(yearMonth);
    }

    public BudgetDate(LocalDate date) {
        this.date = date.withDayOfMonth(1);
    }

    public BudgetDate(YearMonth date) {
        this.date = date.atDay(1);
    }

    public LocalDate asLocalDate() {
        return date;
    }

    public YearMonth asYearMonth() {
        return YearMonth.from(date);
    }
}
