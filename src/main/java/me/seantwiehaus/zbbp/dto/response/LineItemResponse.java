package me.seantwiehaus.zbbp.dto.response;

import lombok.Getter;
import me.seantwiehaus.zbbp.domain.BudgetMonth;
import me.seantwiehaus.zbbp.domain.LineItem;
import me.seantwiehaus.zbbp.domain.Money;

import java.time.LocalDate;
import java.util.List;

@Getter
public class LineItemResponse extends BaseResponse {
  private final Long id;
  private final String name;
  private final Long categoryId;
  private final Double plannedAmount;
  /**
   * Day of Month will be set to the 1st
   */
  private final LocalDate budgetDate;
  private final Double spentAmount;
  private final Double spentPercent;
  private final Double remainingAmount;
  private final Double remainingPercent;
  /**
   * Unmodifiable List
   */
  private final List<TransactionResponse> transactionResponses;

  public LineItemResponse(LineItem lineItem) {
    super(lineItem.getLastModifiedAt());
    this.id = lineItem.getId();
    this.name = lineItem.getName();
    this.categoryId = lineItem.getCategoryId();
    this.plannedAmount = lineItem.getPlannedAmount().inDollars();
    this.budgetDate = lineItem.getBudgetMonth().asLocalDate();
    this.spentAmount = lineItem.getSpentAmount().inDollars();
    this.spentPercent = lineItem.getSpentPercent();
    this.remainingAmount = lineItem.getRemainingAmount().inDollars();
    this.remainingPercent = lineItem.getRemainingPercent();
    this.transactionResponses = lineItem.getTransactions()
        .stream()
        .map(TransactionResponse::new)
        .toList();
  }

  public LineItem convertToLineItem() {
    return new LineItem(
        lastModifiedAt,
        id,
        name,
        categoryId,
        new Money(plannedAmount),
        new BudgetMonth(budgetDate),
        transactionResponses.stream()
            .map(TransactionResponse::convertToTransaction)
            .toList()
    );
  }
}
