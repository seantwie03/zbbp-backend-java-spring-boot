package me.seantwiehaus.zbbp.domain;

import java.time.Instant;
import java.time.LocalDate;

public record Transaction(
    Long id,
    LocalDate date,
    String merchant,
    int amount,
    Long lineItemId,
    String description,
    Instant lastModifiedAt) {
}
