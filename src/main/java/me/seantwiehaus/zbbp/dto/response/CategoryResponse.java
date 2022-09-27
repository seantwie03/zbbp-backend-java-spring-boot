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
  private final boolean isIncome;
  /**
   * Unmodifiable List
   */
  private final List<LineItemResponse> lineItemResponses;

  public CategoryResponse(Category category) {
    super(category.getType(), category.getLastModifiedAt());
    this.id = category.getId();
    this.budgetDate = category.getBudgetMonth().asLocalDate();
    this.name = category.getName();
    this.isIncome = category.isIncome();
    this.lineItemResponses = category.getLineItems()
        .stream()
        .map(LineItemResponse::new)
        .toList();
  }
}
