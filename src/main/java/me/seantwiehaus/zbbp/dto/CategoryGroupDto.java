package me.seantwiehaus.zbbp.dto;

import lombok.Getter;
import me.seantwiehaus.zbbp.domain.CategoryGroup;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Getter
public class CategoryGroupDto extends BaseDto {
    private final Long id;
    @NotBlank
    private final String name;
    /**
     * Day of Month will be set to the 1st
     */
    @NotNull
    private final LocalDate budgetDate;
    /**
     * Unmodifiable List
     */
    @NotNull
    private final List<CategoryDto> categories;

    public CategoryGroupDto(String name,
                            LocalDate budgetDate,
                            List<CategoryDto> categories) {
        this(null, null, null, null, name, budgetDate, categories);
    }

    public CategoryGroupDto(Integer version,
                            Instant createdAt,
                            Instant modifiedAt,
                            Long id,
                            String name,
                            LocalDate budgetDate,
                            List<CategoryDto> categories) {
        super(version, createdAt, modifiedAt);
        this.id = id;
        this.name = name;
        this.budgetDate = budgetDate.withDayOfMonth(1);
        this.categories = Collections.unmodifiableList(categories);
    }

    public CategoryGroupDto(CategoryGroup categoryGroup) {
        super(categoryGroup.getVersion(), categoryGroup.getCreatedAt(), categoryGroup.getLastModifiedAt());
        this.id = categoryGroup.getId();
        this.name = categoryGroup.getName();
        this.budgetDate = categoryGroup.getBudgetDate().asLocalDate();
        this.categories = categoryGroup.getCategories().stream()
                .map(CategoryDto::new)
                .toList();
    }
}
