package me.seantwiehaus.zbbp.dto.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import me.seantwiehaus.zbbp.domain.Category;
import me.seantwiehaus.zbbp.domain.LineItem;
import me.seantwiehaus.zbbp.dto.serialize.CentsToDollarsSerializer;

import java.time.Instant;
import java.time.YearMonth;
import java.util.List;

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
    List<TransactionResponse> transactionResponses) {

  public LineItemResponse(LineItem lineItem) {
    this(
        lineItem.id(),
        lineItem.budgetDate(),
        lineItem.name(),
        lineItem.plannedAmount(),
        lineItem.category(),
        lineItem.description(),
        lineItem.lastModifiedAt(),
        lineItem.calculateTotalTransactions(),
        lineItem.calculatePercentageOfPlanned(),
        lineItem.calculateTotalRemaining(),
        lineItem.transactions()
            .stream()
            .map(TransactionResponse::new)
            .toList());
  }
}
