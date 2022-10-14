package me.seantwiehaus.zbbp.dto.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import me.seantwiehaus.zbbp.domain.Category;
import me.seantwiehaus.zbbp.domain.LineItem;
import me.seantwiehaus.zbbp.dto.serialize.CentsToDollarsSerializer;

import java.time.YearMonth;
import java.util.List;

@Getter
public class LineItemResponse extends BaseResponse {
  private final Long id;
  private final YearMonth budgetDate;
  private final String name;
  @JsonSerialize(using = CentsToDollarsSerializer.class)
  private final Integer plannedAmount;
  private final Category category;

  private final String description;
  @JsonSerialize(using = CentsToDollarsSerializer.class)
  private final Integer totalTransactions;
  private final Double percentageOfPlanned;
  @JsonSerialize(using = CentsToDollarsSerializer.class)
  private final Integer totalRemaining;

  /**
   * Unmodifiable List
   */
  private final List<TransactionResponse> transactionResponses;

  public LineItemResponse(LineItem lineItem) {
    super(lineItem.getLastModifiedAt());
    this.id = lineItem.getId();
    this.budgetDate = lineItem.getBudgetDate();
    this.name = lineItem.getName();
    this.plannedAmount = lineItem.getPlannedAmount();
    this.category = lineItem.getCategory();
    this.description = lineItem.getDescription();
    this.transactionResponses = lineItem.getTransactions()
        .stream()
        .map(TransactionResponse::new)
        .toList();

    this.totalTransactions = lineItem.getTotalTransactions();
    this.percentageOfPlanned = lineItem.getPercentageOfPlanned();
    this.totalRemaining = lineItem.getTotalRemaining();
  }
}
