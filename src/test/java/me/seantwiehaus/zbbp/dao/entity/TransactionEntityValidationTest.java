package me.seantwiehaus.zbbp.dao.entity;

import me.seantwiehaus.zbbp.domain.ItemType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TransactionEntityValidationTest {
  private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
  private TransactionEntity entity;

  @BeforeEach
  void setup() {
    entity = new TransactionEntity();
    entity.setType(ItemType.EXPENSE);
    entity.setDate(LocalDate.now());
    entity.setMerchant("Merchant");
    entity.setAmount(120000);
  }

  @Test
  void shouldNotAllowNullDate() {
    entity.setDate(null);

    Set<ConstraintViolation<TransactionEntity>> violations = validator.validate(entity);

    assertEquals(1, violations.size());
    assertTrue(violations.stream().allMatch(v -> v.getMessage().equals("must not be null")));
  }

  @Test
  void shouldNotAllowNullMerchant() {
    entity.setMerchant(null);

    Set<ConstraintViolation<TransactionEntity>> violations = validator.validate(entity);

    assertEquals(1, violations.size());
    assertTrue(violations.stream().allMatch(v -> v.getMessage().equals("must not be blank")));
  }

  @Test
  void shouldNotAllowBlankMerchant() {
    entity.setMerchant(" ");

    Set<ConstraintViolation<TransactionEntity>> violations = validator.validate(entity);

    assertEquals(1, violations.size());
    assertTrue(violations.stream().allMatch(v -> v.getMessage().equals("must not be blank")));
  }

  @Test
  void shouldNotAllowNegativeAmount() {
    entity.setAmount(- 1);

    Set<ConstraintViolation<TransactionEntity>> violations = validator.validate(entity);

    assertEquals(1, violations.size());
    assertTrue(violations.stream().allMatch(v -> v.getMessage().equals("must be greater than or equal to 0")));
  }

  @Test
  void shouldNotAllowNegativeLineItemId() {
    entity.setLineItemId(- 1L);

    Set<ConstraintViolation<TransactionEntity>> violations = validator.validate(entity);

    assertEquals(1, violations.size());
    assertTrue(violations.stream().allMatch(v -> v.getMessage().equals("must be greater than or equal to 0")));
  }
}
