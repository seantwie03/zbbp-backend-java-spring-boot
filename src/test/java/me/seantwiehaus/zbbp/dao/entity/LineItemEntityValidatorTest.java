package me.seantwiehaus.zbbp.dao.entity;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import me.seantwiehaus.zbbp.domain.Category;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.YearMonth;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LineItemEntityValidatorTest {
  private static Validator validator;
  private LineItemEntity entity;

  @BeforeAll
  static void setupAll() {
    // buildDefaultValidatorFactory is auto-closable. I don't it really matters for a short-lived test class like this,
    // but it doesn't hurt to wrap it in a try-with-resources.
    try (ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
      validator = validatorFactory.getValidator();
    }
  }

  @BeforeEach
  void setup() {
    entity = new LineItemEntity();
    entity.setBudgetDate(YearMonth.now());
    entity.setName("Name");
    entity.setPlannedAmount(120000);
    entity.setCategory(Category.LIFESTYLE);
  }

  @Test
  void budgetDateMustNotBeNull() {
    entity.setBudgetDate(null);

    Set<ConstraintViolation<LineItemEntity>> violations = validator.validate(entity);

    assertEquals(1, violations.size());
    assertTrue(violations.stream().allMatch(v -> v.getMessage().equals("must not be null")));
  }

  @Test
  void nameMustNotBeNull() {
    entity.setName(null);

    Set<ConstraintViolation<LineItemEntity>> violations = validator.validate(entity);

    assertEquals(1, violations.size());
    assertTrue(violations.stream().allMatch(v -> v.getMessage().equals("must not be blank")));
  }

  @Test
  void nameMustNotBeBlank() {
    entity.setName(" ");

    Set<ConstraintViolation<LineItemEntity>> violations = validator.validate(entity);

    assertEquals(1, violations.size());
    assertTrue(violations.stream().allMatch(v -> v.getMessage().equals("must not be blank")));
  }

  @Test
  void plannedAmountMustNotBeNegative() {
    entity.setPlannedAmount(-1);

    Set<ConstraintViolation<LineItemEntity>> violations = validator.validate(entity);

    assertEquals(1, violations.size());
    assertTrue(violations.stream().allMatch(v -> v.getMessage().equals("must be greater than or equal to 0")));
  }

  @Test
  void categoryMustNotBeNull() {
    entity.setCategory(null);

    Set<ConstraintViolation<LineItemEntity>> violations = validator.validate(entity);

    assertEquals(1, violations.size());
    assertTrue(violations.stream().allMatch(v -> v.getMessage().equals("must not be null")));
  }
}
