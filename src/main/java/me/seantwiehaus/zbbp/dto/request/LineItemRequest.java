package me.seantwiehaus.zbbp.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import me.seantwiehaus.zbbp.domain.Category;
import me.seantwiehaus.zbbp.domain.ItemType;
import me.seantwiehaus.zbbp.domain.LineItem;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.YearMonth;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class LineItemRequest {
  @NotNull
  private ItemType type;
  @NotNull
  private YearMonth budgetDate;
  @NotBlank
  private String name;
  @NotNull
  @Min(0)
  private Double plannedAmount;
  @NotNull
  private Category category;
  private String description;

  public LineItem convertToLineItem() {
    return new LineItem(
        type,
        budgetDate,
        name,
        plannedAmount,
        category);
  }
}
