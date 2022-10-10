package me.seantwiehaus.zbbp.dto.response;

import lombok.Getter;
import me.seantwiehaus.zbbp.domain.Category;
import me.seantwiehaus.zbbp.domain.LineItem;

import java.time.YearMonth;
import java.util.List;

@Getter
public class LineItemResponse extends BaseResponse {
  private final Long id;
  private final YearMonth budgetDate;
  private final String name;
  private final Double plannedAmount;
  private final Category category;

  private final String description;
  private final Double totalTransactions;
  private final Double percentageOfPlanned;
  private final Double totalRemaining;

  /**
   * Unmodifiable List
   */
  private final List<TransactionResponse> transactionResponses;

  public LineItemResponse(LineItem lineItem) {
    super(lineItem.getType(), lineItem.getLastModifiedAt());
    this.id = lineItem.getId();
    this.budgetDate = lineItem.getBudgetDate();
    this.name = lineItem.getName();
    this.plannedAmount = lineItem.getPlannedAmount().inDollars();
    this.category = lineItem.getCategory();
    this.description = lineItem.getDescription();
    this.transactionResponses = lineItem.getTransactions()
        .stream()
        .map(TransactionResponse::new)
        .toList();

    this.totalTransactions = lineItem.getTotalTransactions().inDollars();
    this.percentageOfPlanned = lineItem.getPercentageOfPlanned();
    this.totalRemaining = lineItem.getTotalRemaining().inDollars();
  }
}
