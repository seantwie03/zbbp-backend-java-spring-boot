package me.seantwiehaus.zbbp.domain;

import lombok.Getter;

import java.time.LocalDate;

/**
 * Start and Ending BudgetMonths in a Range. There are several places in this app where a range of BudgetMonths
 * are used. For example, when querying a specific budget category (i.e. "Food") over several BudgetMonths.
 * This BudgetMonthRange class is used to add type safety and consistency to this logic.
 */
@Getter
public class BudgetMonthRange {
    private BudgetMonth start = new BudgetMonth(LocalDate.now().minusYears(100));
    private BudgetMonth end = new BudgetMonth(LocalDate.now().plusYears(100));

    /**
     * @param start The starting budgetDate in a range. If null, this property will be set to the current year and
     *              month minus 100 years.
     * @param end   The ending budgetDate in a range. If null, this property will be set to the current year and
     *              month plus 100 years
     */
    public BudgetMonthRange(BudgetMonth start, BudgetMonth end) {
        if (start != null) this.start = start;
        if (end != null) this.end = end;
    }

}
