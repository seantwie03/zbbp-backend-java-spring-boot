package me.seantwiehaus.zbbp.domain;

import lombok.Getter;
import lombok.ToString;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@ToString
@SuppressWarnings("java:S107")
public class Transaction extends BaseDomain {
  private final Long id;
  private final LocalDate date;
  private final String merchant;
  private final Money amount;
  private final Long lineItemId;
  private final String description;

  public Transaction(Long id,
                     ItemType type,
                     LocalDate date,
                     String merchant,
                     Money amount,
                     Long lineItemId,
                     String description) {
    this(id, type, date, merchant, amount, lineItemId, description, null);
  }

  public Transaction(ItemType type,
                     LocalDate date,
                     String merchant,
                     Double amountInDollars,
                     Long lineItemId,
                     String description) {
    this(null, type, date, merchant, new Money(amountInDollars), lineItemId, description, null);
  }

  public Transaction(Long id,
                     ItemType type,
                     LocalDate date,
                     String merchant,
                     Integer amountInCents,
                     Long lineItemId,
                     String description,
                     Instant lastModifiedAt) {
    this(id, type, date, merchant, new Money(amountInCents), lineItemId, description, lastModifiedAt);
  }

  public Transaction(Long id,
                     ItemType type,
                     LocalDate date,
                     String merchant,
                     Money amount,
                     Long lineItemId,
                     String description,
                     Instant lastModifiedAt) {
    super(type, lastModifiedAt);
    this.id = id;
    this.date = date;
    this.merchant = merchant;
    this.amount = amount;
    this.lineItemId = lineItemId;
    this.description = description;
  }
}
