package me.seantwiehaus.zbbp.domain;

import lombok.Builder;
import me.seantwiehaus.zbbp.dto.response.DollarsToCentsConverter;

import java.time.Instant;
import java.time.LocalDate;

@Builder
public record Transaction(
        Long id,
        LocalDate date,
        String merchant,
        int amount,
        Long lineItemId,
        String description,
        Instant lastModifiedAt) {
  public static TransactionBuilder builder(LocalDate date, String merchant, int amount) {
    return new TransactionBuilder().date(date).merchant(merchant).amount(amount);
  }

  /**
   * Additional builder to convert amount from dollars to cents
   */
  public static TransactionBuilder builder(LocalDate date, String merchant, double amount) {
    return new TransactionBuilder().date(date).merchant(merchant).amount(DollarsToCentsConverter.convert(amount));
  }
}
