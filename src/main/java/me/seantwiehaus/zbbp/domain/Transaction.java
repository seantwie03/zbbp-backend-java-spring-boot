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
  private final boolean isIncome;
  private final Long lineItemId;
  private final String description;

  public Transaction(Long id,
                     LocalDate date,
                     String merchant,
                     Money amount,
                     Long lineItemId,
                     String description,
                     Instant lastModifiedAt) {
    this(id, date, merchant, amount, false, lineItemId, description, lastModifiedAt);
  }

  public Transaction(Long id,
                     LocalDate date,
                     String merchant,
                     Double amountInDollars,
                     Long lineItemId,
                     String description,
                     Instant lastModifiedAt) {
    this(id, date, merchant, new Money(amountInDollars), false, lineItemId, description, lastModifiedAt);
  }

  public Transaction(Long id,
                     LocalDate date,
                     String merchant,
                     Integer amountInCents,
                     boolean isIncome,
                     Long lineItemId,
                     String description,
                     Instant lastModifiedAt) {
    this(id, date, merchant, new Money(amountInCents), isIncome, lineItemId, description, lastModifiedAt);
  }

  public Transaction(Long id,
                     LocalDate date,
                     String merchant,
                     Money amount,
                     boolean isIncome,
                     Long lineItemId,
                     String description,
                     Instant lastModifiedAt) {
    super(lastModifiedAt);
    this.id = id;
    this.date = date;
    this.merchant = merchant;
    this.amount = amount;
    this.isIncome = isIncome;
    this.lineItemId = lineItemId;
    this.description = description;
  }
}
