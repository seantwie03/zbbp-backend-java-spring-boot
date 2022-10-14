package me.seantwiehaus.zbbp.domain;

import lombok.Getter;
import lombok.ToString;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@ToString
public class Transaction {
  private final Long id;
  private final LocalDate date;
  private final String merchant;
  private final int amount;
  private final Long lineItemId;
  private final String description;
  private final Instant lastModifiedAt;

  public Transaction(LocalDate date,
                     String merchant,
                     int amount,
                     Long lineItemId,
                     String description) {
    this(null, date, merchant, amount, lineItemId, description, null);
  }

  public Transaction(Long id,
                     LocalDate date,
                     String merchant,
                     int amount,
                     Long lineItemId,
                     String description) {
    this(id, date, merchant, amount, lineItemId, description, null);
  }

  public Transaction(Long id,
                     LocalDate date,
                     String merchant,
                     int amount,
                     Long lineItemId,
                     String description,
                     Instant lastModifiedAt) {
    this.id = id;
    this.date = date;
    this.merchant = merchant;
    this.amount = amount;
    this.lineItemId = lineItemId;
    this.description = description;
    this.lastModifiedAt = lastModifiedAt;
  }
}
