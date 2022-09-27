package me.seantwiehaus.zbbp.dto.response;

import lombok.Getter;
import me.seantwiehaus.zbbp.domain.LineItem;

import java.time.LocalDate;
import java.util.List;

@Getter
public class LineItemResponse extends BaseResponse {
  private final Long id;
  /**
   * Day of Month will be set to the 1st
   */
  private final LocalDate budgetDate;
  private final String name;
  private final Double plannedAmount;
  private final Long categoryId;
  private final String description;
  private final Double spentAmount;
  private final Double spentPercent;
  private final Double remainingAmount;
  private final Double remainingPercent;
  /**
   * Unmodifiable List
   */
  private final List<TransactionResponse> transactionResponses;

  public LineItemResponse(LineItem lineItem) {
    super(lineItem.getType(), lineItem.getLastModifiedAt());
    this.id = lineItem.getId();
    this.budgetDate = lineItem.getBudgetMonth().asLocalDate();
    this.name = lineItem.getName();
    this.plannedAmount = lineItem.getPlannedAmount().inDollars();
    this.categoryId = lineItem.getCategoryId();
    this.description = lineItem.getDescription();
    this.spentAmount = lineItem.getTotalSpent().inDollars();
    this.spentPercent = lineItem.getPercentageSpent();
    this.remainingAmount = lineItem.getTotalRemaining().inDollars();
    this.remainingPercent = lineItem.getPercentageRemaining();
    this.transactionResponses = lineItem.getTransactions()
        .stream()
        .map(TransactionResponse::new)
        .toList();
  }
}
