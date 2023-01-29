package me.seantwiehaus.zbbp.dto.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import me.seantwiehaus.zbbp.domain.Category;
import me.seantwiehaus.zbbp.dto.serialize.CentsToDollarsSerializer;

import java.time.Instant;
import java.time.YearMonth;
import java.util.List;

/**
 * @param transactions Unmodifiable List
 */
public record LineItemResponse(
        Long id,
        YearMonth budgetDate,
        String name,
        @JsonSerialize(using = CentsToDollarsSerializer.class) Integer plannedAmount,
        Category category,
        String description,
        Instant lastModifiedAt,
        @JsonSerialize(using = CentsToDollarsSerializer.class) Integer totalTransactions,
        Double percentageOfPlanned,
        @JsonSerialize(using = CentsToDollarsSerializer.class) Integer totalRemaining,
        List<TransactionResponse> transactions) {
  public LineItemResponse {
    transactions = transactions != null ? List.copyOf(transactions) : List.of();
  }
}
