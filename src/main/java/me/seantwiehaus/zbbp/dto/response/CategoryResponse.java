package me.seantwiehaus.zbbp.dto.response;

import lombok.Getter;
import me.seantwiehaus.zbbp.domain.Category;

import java.time.LocalDate;
import java.util.List;

@Getter
public class CategoryResponse extends BaseResponse {
  private final Long id;
  /**
   * Day of Month will be set to the 1st
   */
  private final LocalDate budgetDate;
  private final String name;
  /**
   * Unmodifiable List
   */
  private final List<LineItemResponse> lineItemResponses;

  private final Double totalTransactions;
  private final Double percentageTransacted;
  private final Double totalRemaining;
  private final Double percentageRemaining;

  public CategoryResponse(Category category) {
    super(category.getType(), category.getLastModifiedAt());
    this.id = category.getId();
    this.budgetDate = category.getBudgetMonth().asLocalDate();
    this.name = category.getName();
    this.lineItemResponses = category.getLineItems()
        .stream()
        .map(LineItemResponse::new)
        .toList();

    this.totalTransactions = category.calculateTotalTransactions().inDollars();
    this.percentageTransacted = category.calculatePercentageTransacted();
    this.totalRemaining = category.calculateTotalRemaining().inDollars();
    this.percentageRemaining = category.calculatePercentageRemaining();
  }
}
