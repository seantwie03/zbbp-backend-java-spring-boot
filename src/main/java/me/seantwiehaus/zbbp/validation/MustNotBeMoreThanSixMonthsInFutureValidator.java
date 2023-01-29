package me.seantwiehaus.zbbp.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.YearMonth;

public class MustNotBeMoreThanSixMonthsInFutureValidator implements
        ConstraintValidator<MustNotBeMoreThanSixMonthsInFuture, YearMonth> {
  @Override
  public boolean isValid(YearMonth yearMonth, ConstraintValidatorContext constraintValidatorContext) {
    return YearMonth.now().plusMonths(7L).isAfter(yearMonth);
  }
}
