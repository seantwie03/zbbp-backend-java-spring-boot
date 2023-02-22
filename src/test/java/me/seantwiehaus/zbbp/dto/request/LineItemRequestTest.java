package me.seantwiehaus.zbbp.dto.request;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import me.seantwiehaus.zbbp.domain.Category;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.time.YearMonth;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LineItemRequestTest {
  private static Validator validator;

  @BeforeAll
  static void setupAll() {
    // buildDefaultValidatorFactory is auto-closable. I don't think it really matters for a short-lived test class
    // like this, but it doesn't hurt to wrap it in a try-with-resources.
    try (ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
      validator = validatorFactory.getValidator();
    }
  }

  @Test
  void budgetDateMustNotBeNull() {
    LineItemRequest request = new LineItemRequest(null, "Name", 1.00, Category.FOOD, "Description");

    Set<ConstraintViolation<LineItemRequest>> violations = validator.validate(request);

    assertEquals(1, violations.size());
    assertTrue(violations.stream().allMatch(v -> v.getMessage().equals("must not be null")));
  }

  @ParameterizedTest
  @NullAndEmptySource
  void nameMustBeValid(String name) {
    LineItemRequest request = new LineItemRequest(YearMonth.now(), name, 1.00, Category.FOOD, "Description");

    Set<ConstraintViolation<LineItemRequest>> violations = validator.validate(request);

    assertEquals(1, violations.size());
    assertTrue(violations.stream().allMatch(v -> v.getMessage().equals("must not be blank")));
  }

  @Test
  void plannedAmountMustNotBeNegative() {
    LineItemRequest request = new LineItemRequest(YearMonth.now(), "Name", -1.00, Category.FOOD, "Description");

    Set<ConstraintViolation<LineItemRequest>> violations = validator.validate(request);

    assertEquals(1, violations.size());
    assertTrue(violations.stream().allMatch(v -> v.getMessage().equals("must be greater than or equal to 0")));
  }

  @Test
  void plannedAmountMustNotBeGreaterThanMaxIntegerWhenConvertedToCents() {
    LineItemRequest request = new LineItemRequest(YearMonth.now(), "Name", 21_474_835.01, Category.FOOD, "Description");

    Set<ConstraintViolation<LineItemRequest>> violations = validator.validate(request);

    assertEquals(1, violations.size());
    assertTrue(violations.stream().allMatch(v -> v.getMessage().equals("must be less than or equal to 21474835")));
  }

  @Test
  void categoryMustNotBeNull() {
    LineItemRequest request = new LineItemRequest(YearMonth.now(), "Name", 1.00, null, "Description");

    Set<ConstraintViolation<LineItemRequest>> violations = validator.validate(request);

    assertEquals(1, violations.size());
    assertTrue(violations.stream().allMatch(v -> v.getMessage().equals("must not be null")));
  }
}
