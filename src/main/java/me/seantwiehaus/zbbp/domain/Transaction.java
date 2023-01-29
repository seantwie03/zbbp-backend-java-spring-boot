package me.seantwiehaus.zbbp.domain;

import lombok.Builder;

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
}
