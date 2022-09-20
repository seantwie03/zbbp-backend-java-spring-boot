package me.seantwiehaus.zbbp.domain;

import lombok.Getter;
import lombok.ToString;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

@Getter
@ToString
public class CategoryGroup extends BaseDomain {
  private final Long id;
  private final String name;
  private final BudgetMonth budgetMonth;
  /**
   * Unmodifiable List
   */
  private final List<Category> categories;

  public CategoryGroup(Instant lastModifiedAt,
                       Long id,
                       String name,
                       BudgetMonth budgetMonth,
                       List<Category> categories) {
    super(lastModifiedAt);
    this.id = id;
    this.name = name;
    this.budgetMonth = budgetMonth;
    this.categories = Collections.unmodifiableList(categories);
  }
}
