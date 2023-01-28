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
@Constraint(validatedBy = MustNotBeMoreThanSixMonthsInFutureValidator.class)
@Documented
public @interface MustNotBeMoreThanSixMonthsInFuture {
  String message() default "This BudgetDate must not be more than six months in the future.";

  Class<?>[] groups() default { };

  Class<? extends Payload>[] payload() default { };
}
