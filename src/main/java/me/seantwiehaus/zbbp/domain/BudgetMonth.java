package me.seantwiehaus.zbbp.domain;

import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Objects;

/**
 * A BudgetMonth is a Year/Month combination. For compatability with database types and REST API clients this app will
 * use a standard ISO 8601 Date format; however, in the business logic this custom BudgetMonth class will be used to
 * ensure that if a Day portion of the date is needed, it will always be set to the 1st.
 */
@Getter
@ToString
public class BudgetMonth {
  private YearMonth date = YearMonth.of(LocalDate.now().getYear(), LocalDate.now().getMonth());

  public BudgetMonth() {
  }

  public BudgetMonth(@NotNull LocalDate date) {
    this.date = YearMonth.of(date.getYear(), date.getMonth());
  }

  public LocalDate asLocalDate() {
    return date.atDay(1);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    BudgetMonth that = (BudgetMonth) o;
    return date.equals(that.date);
  }

  @Override
  public int hashCode() {
    return Objects.hash(date);
  }
}
