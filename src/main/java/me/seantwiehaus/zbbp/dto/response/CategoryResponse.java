package me.seantwiehaus.zbbp.dto.response;

import lombok.Getter;
import me.seantwiehaus.zbbp.domain.Category;

import java.time.LocalDate;
import java.util.List;

@Getter
public class CategoryResponse extends BaseResponse {
  private final Long id;
  private final String name;
  /**
   * Day of Month will be set to the 1st
   */
  private final LocalDate budgetDate;
  /**
   * Unmodifiable List
   */
  private final List<LineItemResponse> lineItemResponses;

  public CategoryResponse(Category category) {
    super(category.getLastModifiedAt());
    this.id = category.getId();
    this.name = category.getName();
    this.budgetDate = category.getBudgetMonth().asLocalDate();
    this.lineItemResponses = category.getLineItems()
        .stream()
        .map(LineItemResponse::new)
        .toList();
  }
}
