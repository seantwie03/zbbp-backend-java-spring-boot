package me.seantwiehaus.zbbp.domain;

import lombok.Getter;

import java.time.Instant;
import java.util.List;

@Getter
public class CategoryGroup extends BaseDomain {
    private final Long id;
    private final String name;
    private final BudgetDate budgetDate;
    private final List<Category> categories;

    public CategoryGroup(Instant lastModifiedAt,
                         Long id,
                         String name,
                         BudgetDate budgetDate,
                         List<Category> categories) {
        super(lastModifiedAt);
        this.id = id;
        this.name = name;
        this.budgetDate = budgetDate;
        this.categories = categories;
    }
}
