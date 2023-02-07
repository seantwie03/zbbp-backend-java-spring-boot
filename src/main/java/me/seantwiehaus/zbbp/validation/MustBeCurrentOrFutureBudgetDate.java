package me.seantwiehaus.zbbp.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD, PARAMETER })
@Retention(RUNTIME)
@Constraint(validatedBy = MustBeCurrentOrFutureBudgetDateValidator.class)
@Documented
public @interface MustBeCurrentOrFutureBudgetDate {

  String message() default "This BudgetDate must be a current or future year and month.";

  Class<?>[] groups() default { };

  Class<? extends Payload>[] payload() default { };
}
