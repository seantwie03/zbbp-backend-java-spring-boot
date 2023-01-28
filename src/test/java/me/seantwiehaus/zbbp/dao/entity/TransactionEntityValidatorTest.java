package me.seantwiehaus.zbbp.dao.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TransactionEntityValidatorTest {
  private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
  private TransactionEntity entity;

  @BeforeEach
  void setup() {
    entity = new TransactionEntity();
    entity.setDate(LocalDate.now());
    entity.setMerchant("Merchant");
    entity.setAmount(120000);
  }

  @Test
  void dateMustNotBeNull() {
    entity.setDate(null);

    Set<ConstraintViolation<TransactionEntity>> violations = validator.validate(entity);

    assertEquals(1, violations.size());
    assertTrue(violations.stream().allMatch(v -> v.getMessage().equals("must not be null")));
  }

  @Test
  void merchantMustNotBeNull() {
    entity.setMerchant(null);

    Set<ConstraintViolation<TransactionEntity>> violations = validator.validate(entity);

    assertEquals(1, violations.size());
    assertTrue(violations.stream().allMatch(v -> v.getMessage().equals("must not be blank")));
  }

  @Test
  void merchantMustNotBeEmpty() {
    entity.setMerchant(" ");

    Set<ConstraintViolation<TransactionEntity>> violations = validator.validate(entity);

    assertEquals(1, violations.size());
    assertTrue(violations.stream().allMatch(v -> v.getMessage().equals("must not be blank")));
  }

  @Test
  void amountMustNotBeNegative() {
    entity.setAmount(- 1);

    Set<ConstraintViolation<TransactionEntity>> violations = validator.validate(entity);

    assertEquals(1, violations.size());
    assertTrue(violations.stream().allMatch(v -> v.getMessage().equals("must be greater than or equal to 0")));
  }

  @Test
  void lineItemIdMustNotBeNegative() {
    entity.setLineItemId(- 1L);

    Set<ConstraintViolation<TransactionEntity>> violations = validator.validate(entity);

    assertEquals(1, violations.size());
    assertTrue(violations.stream().allMatch(v -> v.getMessage().equals("must be greater than or equal to 0")));
  }
}
