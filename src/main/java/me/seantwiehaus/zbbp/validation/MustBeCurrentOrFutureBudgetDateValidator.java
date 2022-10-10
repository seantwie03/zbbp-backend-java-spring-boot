package me.seantwiehaus.zbbp.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.YearMonth;

public class MustBeCurrentOrFutureBudgetDateValidator implements
    ConstraintValidator<MustBeCurrentOrFutureBudgetDate, YearMonth> {
  @Override
  public boolean isValid(YearMonth yearMonth, ConstraintValidatorContext constraintValidatorContext) {
    YearMonth current = YearMonth.now();
    return yearMonth.equals(current) || yearMonth.isAfter(current);
  }
}
