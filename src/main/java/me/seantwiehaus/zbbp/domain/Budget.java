package me.seantwiehaus.zbbp.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.YearMonth;
import java.util.Set;

@Getter
@AllArgsConstructor
public class Budget {
    private final YearMonth budgetDate;
    private final Set<CategoryGroup> categoryGroups;
}
