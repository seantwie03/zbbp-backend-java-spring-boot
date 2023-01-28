package me.seantwiehaus.zbbp.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.YearMonth;

public class MustBeCurrentOrFutureBudgetDateValidator implements
    ConstraintValidator<MustBeCurrentOrFutureBudgetDate, YearMonth> {
  @Override
  public boolean isValid(YearMonth yearMonth, ConstraintValidatorContext constraintValidatorContext) {
    YearMonth current = YearMonth.now();
    return yearMonth.equals(current) || yearMonth.isAfter(current);
  }
}
