package me.seantwiehaus.zbbp.dto.response;

import lombok.Getter;
import me.seantwiehaus.zbbp.domain.CategoryGroup;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Collections;
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
    private final List<CategoryResponse> categories;

    public CategoryGroupResponse(String name,
                                 LocalDate budgetDate,
                                 List<CategoryResponse> categories) {
        this(null, null, name, budgetDate, categories);
    }

    public CategoryGroupResponse(Instant modifiedAt,
                                 Long id,
                                 String name,
                                 LocalDate budgetDate,
                                 List<CategoryResponse> categories) {
        super(modifiedAt);
        this.id = id;
        this.name = name;
        this.budgetDate = budgetDate.withDayOfMonth(1);
        this.categories = Collections.unmodifiableList(categories);
    }

    public CategoryGroupResponse(CategoryGroup categoryGroup) {
        super(categoryGroup.getLastModifiedAt());
        this.id = categoryGroup.getId();
        this.name = categoryGroup.getName();
        this.budgetDate = categoryGroup.getBudgetMonth().asLocalDate();
        this.categories = categoryGroup.getCategories()
                .stream()
                .map(CategoryResponse::new)
                .toList();
    }
}
