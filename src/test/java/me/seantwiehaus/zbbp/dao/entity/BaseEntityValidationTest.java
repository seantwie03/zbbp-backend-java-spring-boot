package me.seantwiehaus.zbbp.dao.entity;

import me.seantwiehaus.zbbp.domain.ItemType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BaseEntityValidationTest {
  private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
  private BaseEntity entity;

  @BeforeEach
  void setup() {
    entity = new BaseEntity();
    entity.setType(ItemType.EXPENSE);
  }

  @Test
  void shouldNotAllowNullType() {
    entity.setType(null);

    Set<ConstraintViolation<BaseEntity>> violations = validator.validate(entity);

    assertEquals(1, violations.size());
    assertTrue(violations.stream().allMatch(v -> v.getMessage().equals("must not be null")));
  }
}
