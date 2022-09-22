package me.seantwiehaus.zbbp.domain;

import lombok.Getter;
import lombok.ToString;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

@Getter
@ToString
public class Category extends BaseDomain {
  private final Long id;
  private final String name;
  private final BudgetMonth budgetMonth;
  /**
   * Unmodifiable List
   */
  private final List<LineItem> lineItems;

  public Category(Instant lastModifiedAt,
                  Long id,
                  String name,
                  BudgetMonth budgetMonth,
                  List<LineItem> lineItems) {
    super(lastModifiedAt);
    this.id = id;
    this.name = name;
    this.budgetMonth = budgetMonth;
    this.lineItems = Collections.unmodifiableList(lineItems);
  }
}
