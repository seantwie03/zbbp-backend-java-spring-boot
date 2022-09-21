package me.seantwiehaus.zbbp.dto.response;

import lombok.Getter;
import me.seantwiehaus.zbbp.domain.CategoryGroup;

import java.time.LocalDate;
import java.util.List;

@Getter
public class CategoryGroupResponse extends BaseResponse {
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

  public CategoryGroupResponse(CategoryGroup categoryGroup) {
    super(categoryGroup.getLastModifiedAt());
    this.id = categoryGroup.getId();
    this.name = categoryGroup.getName();
    this.budgetDate = categoryGroup.getBudgetMonth().asLocalDate();
    this.lineItemResponses = categoryGroup.getLineItems()
        .stream()
        .map(LineItemResponse::new)
        .toList();
  }
}
