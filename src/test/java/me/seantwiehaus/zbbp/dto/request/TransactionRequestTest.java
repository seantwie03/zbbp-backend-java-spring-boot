package me.seantwiehaus.zbbp.dto.request;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TransactionRequestTest {
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
  void dateMustNotBeNull() {
    TransactionRequest request = new TransactionRequest(null, "Merchant", 1.00, 1L, "Description");

    Set<ConstraintViolation<TransactionRequest>> violations = validator.validate(request);

    assertEquals(1, violations.size());
    assertTrue(violations.stream().allMatch(v -> v.getMessage().equals("must not be null")));
  }

  @ParameterizedTest
  @NullSource
  void merchantMustNotBeNull(String merchant) {
    TransactionRequest request = new TransactionRequest(LocalDate.now(), merchant, 1.00, 1L, "Description");

    Set<ConstraintViolation<TransactionRequest>> violations = validator.validate(request);

    assertEquals(1, violations.size());
    assertTrue(violations.stream().allMatch(v -> v.getMessage().equals("must not be blank")));
  }

  @ParameterizedTest
  @EmptySource
  void merchantMustNotBeEmpty(String merchant) {
    TransactionRequest request = new TransactionRequest(LocalDate.now(), merchant, 1.00, 1L, "Description");

    Set<ConstraintViolation<TransactionRequest>> violations = validator.validate(request);

    assertEquals(2, violations.size());
    assertTrue(violations.stream()
            .allMatch(v ->
                    v.getMessage().equals("must not be blank")
                            || v.getMessage().equals("size must be between 1 and 50")));
  }

  @Test
  void merchantMustBeGreaterThan0AndLessThanOrEqualTo50Characters() {
    TransactionRequest request = new TransactionRequest(LocalDate.now(),
            "This is >50 characters This is >50 characters This is >50 characters ", 1.00, 1L, "Description");

    Set<ConstraintViolation<TransactionRequest>> violations = validator.validate(request);

    assertEquals(1, violations.size());
    assertTrue(violations.stream().allMatch(v -> v.getMessage().equals("size must be between 1 and 50")));
  }

  @Test
  void amountMustNotBeNegative() {
    TransactionRequest request = new TransactionRequest(LocalDate.now(), "Merchant", -1.00, 1L, "Description");

    Set<ConstraintViolation<TransactionRequest>> violations = validator.validate(request);

    assertEquals(1, violations.size());
    assertTrue(violations.stream().allMatch(v -> v.getMessage().equals("must be greater than or equal to 0")));
  }

  @Test
  void amountMustNotBeGreaterThanMaxIntegerWhenConvertedToCents() {
    TransactionRequest request = new TransactionRequest(LocalDate.now(), "Merchant", 21_474_835.01, 1L, "Description");

    Set<ConstraintViolation<TransactionRequest>> violations = validator.validate(request);

    assertEquals(1, violations.size());
    assertTrue(violations.stream().allMatch(v -> v.getMessage().equals("must be less than or equal to 21474835")));
  }

  @Test
  void descriptionMustBeLessThanOrEqualTo255Characters() {
    TransactionRequest request = new TransactionRequest(LocalDate.now(), "Merchant", 1.00, 1L,
            "This is >255 characters This is >255 characters This is >255 characters This is >255 characters " +
                    "This is >255 characters This is >255 characters This is >255 characters This is >255 characters " +
                    "This is >255 characters This is >255 characters This is >255 characters This is >255 characters " +
                    "This is >255 characters This is >255 characters This is >255 characters This is >255 characters " +
                    "This is >255 characters This is >255 characters ");

    Set<ConstraintViolation<TransactionRequest>> violations = validator.validate(request);

    assertEquals(1, violations.size());
    assertTrue(violations.stream().allMatch(v -> v.getMessage().equals("size must be between 0 and 255")));
  }
}
