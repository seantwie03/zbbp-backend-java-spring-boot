package me.seantwiehaus.zbbp.domain;

import lombok.Getter;
import lombok.ToString;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@ToString
public class Transaction extends BaseDomain {
  private final Long id;
  private final Money amount;
  private final LocalDate date;
  private final String description;
  private final Long categoryId;

  public Transaction(Instant lastModifiedAt,
                     Long id,
                     Money amount,
                     LocalDate date,
                     String description,
                     Long categoryId) {
    super(lastModifiedAt);
    this.id = id;
    this.amount = amount;
    this.date = date;
    this.description = description;
    this.categoryId = categoryId;
  }

  public Transaction(Instant lastModifiedAt,
                     Long id,
                     Double amountInDollars,
                     LocalDate date,
                     String description,
                     Long categoryId) {
    super(lastModifiedAt);
    this.id = id;
    this.amount = new Money(amountInDollars);
    this.date = date;
    this.description = description;
    this.categoryId = categoryId;
  }

  public Transaction(Instant lastModifiedAt,
                     Long id,
                     Integer amountInCents,
                     LocalDate date,
                     String description,
                     Long categoryId) {
    super(lastModifiedAt);
    this.id = id;
    this.amount = new Money(amountInCents);
    this.date = date;
    this.description = description;
    this.categoryId = categoryId;
  }
}
