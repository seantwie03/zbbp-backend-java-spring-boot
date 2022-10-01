package me.seantwiehaus.zbbp.dto.response;

import lombok.Getter;
import me.seantwiehaus.zbbp.domain.Category;
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
  private final Category category;
  private final String description;
  private final Double totalTransactions;
  private final Double percentageTransacted;
  private final Double totalRemaining;
  private final Double percentageRemaining;
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
    this.category = lineItem.getCategory();
    this.description = lineItem.getDescription();
    this.totalTransactions = lineItem.calculateTotalTransactions().inDollars();
    this.percentageTransacted = lineItem.calculatePercentageTransacted();
    this.totalRemaining = lineItem.calculateTotalRemaining().inDollars();
    this.percentageRemaining = lineItem.calculatePercentageRemaining();
    this.transactionResponses = lineItem.getTransactions()
        .stream()
        .map(TransactionResponse::new)
        .toList();
  }
}
