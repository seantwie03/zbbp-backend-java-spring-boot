package me.seantwiehaus.zbbp.dto.response;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransactionResponseTest {
  @Nested
  class ConstructorThatTakesCents {
    @Test
    void convertsAmountToDollars() {
      TransactionResponse transactionResponse = new TransactionResponse(
              1L,
              LocalDate.of(2022, 2, 1),
              "Merchant",
              500_99,
              1L,
              "description",
              Instant.now());

      assertEquals(500.99, transactionResponse.amount());
    }
  }
}
