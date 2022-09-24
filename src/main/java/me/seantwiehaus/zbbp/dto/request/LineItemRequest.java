package me.seantwiehaus.zbbp.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import me.seantwiehaus.zbbp.domain.BudgetMonth;
import me.seantwiehaus.zbbp.domain.LineItem;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class LineItemRequest {
  /**
   * Day of Month will be set to the 1st
   */
  @NotNull
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate budgetDate;
  @NotBlank
  private String name;
  @NotNull
  private Double plannedAmount;
  @NotNull
  private Long categoryId;
  private String description;

  public LineItem convertToLineItem() {
    return new LineItem(
        null,
        new BudgetMonth(budgetDate),
        name,
        plannedAmount,
        categoryId,
        null,
        null);

  }
}
