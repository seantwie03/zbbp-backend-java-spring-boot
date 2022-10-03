package me.seantwiehaus.zbbp.validation;

import me.seantwiehaus.zbbp.domain.BudgetMonth;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class MustNotBeMoreThanSixMonthsInFutureValidator implements
    ConstraintValidator<MustNotBeMoreThanSixMonthsInFuture, LocalDate> {
  @Override
  public boolean isValid(LocalDate localDate, ConstraintValidatorContext constraintValidatorContext) {
    LocalDate current = new BudgetMonth().asLocalDate();
    LocalDate toValidate = new BudgetMonth(localDate).asLocalDate();
    return current.plusMonths(7L).isAfter(toValidate);
  }
}
