package me.seantwiehaus.zbbp.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.YearMonth;

public class MustNotBeMoreThanSixMonthsInFutureValidator implements
    ConstraintValidator<MustNotBeMoreThanSixMonthsInFuture, YearMonth> {
  @Override
  public boolean isValid(YearMonth yearMonth, ConstraintValidatorContext constraintValidatorContext) {
    return YearMonth.now().plusMonths(7L).isAfter(yearMonth);
  }
}
