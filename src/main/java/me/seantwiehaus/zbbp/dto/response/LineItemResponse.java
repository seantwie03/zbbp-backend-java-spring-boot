package me.seantwiehaus.zbbp.dto.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import me.seantwiehaus.zbbp.domain.Category;
import me.seantwiehaus.zbbp.dto.serializer.TwoDecimalPlacesSerializer;

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
        @JsonSerialize(using = TwoDecimalPlacesSerializer.class) Double plannedAmount,
        Category category,
        String description,
        Instant lastModifiedAt,
        @JsonSerialize(using = TwoDecimalPlacesSerializer.class) Double totalTransactions,
        Double percentageOfPlanned,
        @JsonSerialize(using = TwoDecimalPlacesSerializer.class) Double totalRemaining,
        List<TransactionResponse> transactions) {
  public LineItemResponse {
    transactions = transactions != null ? List.copyOf(transactions) : List.of();
  }

  /**
   * Additional constructor to convert monetary amounts from cents to dollars
   */
  public LineItemResponse(Long id,
                          YearMonth budgetDate,
                          String name,
                          Integer plannedAmount,
                          Category category,
                          String description,
                          Instant lastModifiedAt,
                          Integer totalTransactions,
                          Double percentageOfPlanned,
                          Integer totalRemaining,
                          List<TransactionResponse> transactions) {
    this(
            id,
            budgetDate,
            name,
            CentsToDollarsConverter.convert(plannedAmount),
            category,
            description,
            lastModifiedAt,
            CentsToDollarsConverter.convert(totalTransactions),
            percentageOfPlanned,
            CentsToDollarsConverter.convert(totalRemaining),
            transactions);
  }
}
