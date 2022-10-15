package me.seantwiehaus.zbbp.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@ToString
@AllArgsConstructor
public class Transaction {
  private final Long id;
  private final LocalDate date;
  private final String merchant;
  private final int amount;
  private final Long lineItemId;
  private final String description;
  private final Instant lastModifiedAt;
}
