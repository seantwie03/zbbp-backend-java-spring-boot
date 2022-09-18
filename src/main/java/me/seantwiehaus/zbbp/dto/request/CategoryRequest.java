package me.seantwiehaus.zbbp.dto.request;

import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@ToString
public class CategoryRequest {
    @NotBlank
    private final String name;
    @NotNull
    private final Long categoryGroupId;
    @NotNull
    private final BigDecimal plannedAmount;
    /**
     * Day of Month will be set to the 1st
     */
    @NotNull
    private final LocalDate budgetDate;

    public CategoryRequest(String name,
                           Long categoryGroupId,
                           BigDecimal plannedAmount,
                           LocalDate budgetDate) {
        this.name = name;
        this.categoryGroupId = categoryGroupId;
        this.plannedAmount = plannedAmount;
        this.budgetDate = budgetDate;
    }
}
