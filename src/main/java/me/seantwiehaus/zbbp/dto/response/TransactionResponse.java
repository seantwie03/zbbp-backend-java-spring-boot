package me.seantwiehaus.zbbp.dto.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import me.seantwiehaus.zbbp.dto.serializer.TwoDecimalPlacesSerializer;

import java.time.Instant;
import java.time.LocalDate;

public record TransactionResponse(
        Long id,
        LocalDate date,
        String merchant,
        @JsonSerialize(using = TwoDecimalPlacesSerializer.class) Double amount,
        Long lineItemId,
        String description,
        Instant lastModifiedAt) {
  public TransactionResponse(Long id,
                             LocalDate date,
                             String merchant,
                             int amount,
                             Long lineItemId,
                             String description,
                             Instant lastModifiedAt) {
    this(
            id,
            date,
            merchant,
            CentsToDollarsConverter.convert(amount),
            lineItemId,
            description,
            lastModifiedAt);
  }
}
