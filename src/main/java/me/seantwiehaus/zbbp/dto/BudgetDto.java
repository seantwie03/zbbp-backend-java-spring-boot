package me.seantwiehaus.zbbp.dto;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

public record BudgetDto(
        @NotNull LocalDate budgetDate,
        @NotNull Set<CategoryGroupDto> categoryGroupDtos
) {}
