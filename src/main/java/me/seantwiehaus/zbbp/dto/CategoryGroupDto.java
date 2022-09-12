package me.seantwiehaus.zbbp.dto;

import me.seantwiehaus.zbbp.domain.CategoryGroup;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public record CategoryGroupDto(
        Long id,
        @NotBlank String name,
        @NotNull LocalDate budgetDate,
        @NotNull List<CategoryDto> categories
) {
    public CategoryGroupDto(CategoryGroup categoryGroup) {
        this(categoryGroup.getId(),
                categoryGroup.getName(),
                categoryGroup.getBudgetDate().asLocalDate(),
                new ArrayList<>(
                        categoryGroup.getCategories().stream()
                                .map(CategoryDto::new)
                                .toList()));
    }
}
