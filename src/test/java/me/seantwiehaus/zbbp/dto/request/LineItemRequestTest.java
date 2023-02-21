package me.seantwiehaus.zbbp.dto.request;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import me.seantwiehaus.zbbp.domain.Category;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

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

  @Test
  void nameMustNotBeNull() {
    LineItemRequest request = new LineItemRequest(YearMonth.now(), null, 1.00, Category.FOOD, "Description");

    Set<ConstraintViolation<LineItemRequest>> violations = validator.validate(request);

    assertEquals(1, violations.size());
    assertTrue(violations.stream().allMatch(v -> v.getMessage().equals("must not be blank")));
  }

  @Test
  void nameMustNotBeEmpty() {
    LineItemRequest request = new LineItemRequest(YearMonth.now(), " ", 1.00, Category.FOOD, "Description");

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
  void categoryMustNotBeNull() {
    LineItemRequest request = new LineItemRequest(YearMonth.now(), "Name", 1.00, null, "Description");

    Set<ConstraintViolation<LineItemRequest>> violations = validator.validate(request);

    assertEquals(1, violations.size());
    assertTrue(violations.stream().allMatch(v -> v.getMessage().equals("must not be null")));
  }
}
