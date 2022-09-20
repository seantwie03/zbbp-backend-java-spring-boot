package me.seantwiehaus.zbbp.dto.response;

import lombok.Getter;
import me.seantwiehaus.zbbp.domain.BudgetMonth;
import me.seantwiehaus.zbbp.domain.Category;
import me.seantwiehaus.zbbp.domain.Money;

import java.time.LocalDate;
import java.util.List;

@Getter
public class CategoryResponse extends BaseResponse {
  private final Long id;
  private final String name;
  private final Long categoryGroupId;
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

  public CategoryResponse(Category category) {
    super(category.getLastModifiedAt());
    this.id = category.getId();
    this.name = category.getName();
    this.categoryGroupId = category.getCategoryGroupId();
    this.plannedAmount = category.getPlannedAmount().inDollars();
    this.budgetDate = category.getBudgetMonth().asLocalDate();
    this.spentAmount = category.getSpentAmount().inDollars();
    this.spentPercent = category.getSpentPercent();
    this.remainingAmount = category.getRemainingAmount().inDollars();
    this.remainingPercent = category.getRemainingPercent();
    this.transactionResponses = category.getTransactions()
        .stream()
        .map(TransactionResponse::new)
        .toList();
  }

  public Category convertToCategory() {
    return new Category(lastModifiedAt,
        id,
        name,
        categoryGroupId,
        new Money(plannedAmount),
        new BudgetMonth(budgetDate),
        transactionResponses.stream()
            .map(TransactionResponse::convertToTransaction)
            .toList()
    );
  }
}
