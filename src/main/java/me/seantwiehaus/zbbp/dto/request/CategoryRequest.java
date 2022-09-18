package me.seantwiehaus.zbbp.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@ToString
@AllArgsConstructor
public class CategoryRequest {
    @NotBlank
    private final String name;
    @NotNull
    private final Long categoryGroupId;
    @NotNull
    private final Double plannedAmount;
    /**
     * Day of Month will be set to the 1st
     */
    @NotNull
    private final LocalDate budgetDate;
}
