package me.seantwiehaus.zbbp.domain;

import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.time.LocalDate;

@Getter
@ToString
public class Transaction extends BaseDomain {
  private final Long id;
  private final LocalDate date;
  private final String merchant;
  private final int amount;
  private final Long lineItemId;
  private final String description;

  public Transaction(@NotNull ItemType type,
                     @NotNull LocalDate date,
                     @NotNull String merchant,
                     @NotNull int amount,
                     Long lineItemId,
                     String description) {
    this(null, type, date, merchant, amount, lineItemId, description, null);
  }

  public Transaction(Long id,
                     @NotNull ItemType type,
                     @NotNull LocalDate date,
                     @NotNull String merchant,
                     @NotNull int amount,
                     Long lineItemId,
                     String description) {
    this(id, type, date, merchant, amount, lineItemId, description, null);
  }

  @SuppressWarnings("java:S107") // SonarLint thinks this is too many constructor parameters
  public Transaction(Long id,
                     @NotNull ItemType type,
                     @NotNull LocalDate date,
                     @NotNull String merchant,
                     int amount,
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
