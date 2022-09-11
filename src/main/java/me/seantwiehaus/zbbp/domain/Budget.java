package me.seantwiehaus.zbbp.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@AllArgsConstructor
public class Budget {
    private final LocalDate budgetDate;
    private final Set<CategoryGroup> categoryGroups;
}
