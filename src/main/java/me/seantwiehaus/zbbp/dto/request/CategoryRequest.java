package me.seantwiehaus.zbbp.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import me.seantwiehaus.zbbp.domain.BudgetMonth;
import me.seantwiehaus.zbbp.domain.Category;
import me.seantwiehaus.zbbp.domain.Money;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CategoryRequest {
    @NotBlank
    private String name;
    @NotNull
    private Long categoryGroupId;
    @NotNull
    private Double plannedAmount;
    /**
     * Day of Month will be set to the 1st
     */
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate budgetDate;

    public Category convertToCategory() {
        return new Category(
                null,
                null,
                name,
                categoryGroupId,
                new Money(plannedAmount),
                new BudgetMonth(budgetDate),
                null);

    }
}
