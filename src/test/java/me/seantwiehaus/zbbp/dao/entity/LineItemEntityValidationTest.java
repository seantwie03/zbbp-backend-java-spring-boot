package me.seantwiehaus.zbbp.dao.entity;

import me.seantwiehaus.zbbp.domain.Category;
import me.seantwiehaus.zbbp.domain.ItemType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.time.YearMonth;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LineItemEntityValidationTest {
  private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
  private LineItemEntity entity;

  @BeforeEach
  void setup() {
    entity = new LineItemEntity();
    entity.setType(ItemType.EXPENSE);
    entity.setBudgetDate(YearMonth.now());
    entity.setName("Name");
    entity.setPlannedAmount(120000);
    entity.setCategory(Category.LIFESTYLE);
  }

  @Test
  void shouldNotAllowNullBudgetDate() {
    entity.setBudgetDate(null);

    Set<ConstraintViolation<LineItemEntity>> violations = validator.validate(entity);

    assertEquals(1, violations.size());
    assertTrue(violations.stream().allMatch(v -> v.getMessage().equals("must not be null")));
  }

  @Test
  void shouldNotAllowNullName() {
    entity.setName(null);

    Set<ConstraintViolation<LineItemEntity>> violations = validator.validate(entity);

    assertEquals(1, violations.size());
    assertTrue(violations.stream().allMatch(v -> v.getMessage().equals("must not be blank")));
  }

  @Test
  void shouldNotAllowBlankName() {
    entity.setName(" ");

    Set<ConstraintViolation<LineItemEntity>> violations = validator.validate(entity);

    assertEquals(1, violations.size());
    assertTrue(violations.stream().allMatch(v -> v.getMessage().equals("must not be blank")));
  }

  @Test
  void shouldNotAllowNegativePlannedAmount() {
    entity.setPlannedAmount(- 1);

    Set<ConstraintViolation<LineItemEntity>> violations = validator.validate(entity);

    assertEquals(1, violations.size());
    assertTrue(violations.stream().allMatch(v -> v.getMessage().equals("must be greater than or equal to 0")));
  }

  @Test
  void shouldNotAllowNullCategory() {
    entity.setCategory(null);

    Set<ConstraintViolation<LineItemEntity>> violations = validator.validate(entity);

    assertEquals(1, violations.size());
    assertTrue(violations.stream().allMatch(v -> v.getMessage().equals("must not be null")));
  }
}
