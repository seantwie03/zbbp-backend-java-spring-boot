package me.seantwiehaus.zbbp.dto.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import me.seantwiehaus.zbbp.dto.serializer.CentsToDollarsSerializer;

import java.time.Instant;
import java.time.LocalDate;

public record TransactionResponse(
        Long id,
        LocalDate date,
        String merchant,
        @JsonSerialize(using = CentsToDollarsSerializer.class) int amount,
        Long lineItemId,
        String description,
        Instant lastModifiedAt) {
}
