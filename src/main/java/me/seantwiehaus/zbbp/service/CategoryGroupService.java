package me.seantwiehaus.zbbp.service;

import me.seantwiehaus.zbbp.dao.entity.CategoryGroupEntity;
import me.seantwiehaus.zbbp.dao.repository.CategoryGroupRepository;
import me.seantwiehaus.zbbp.domain.BudgetMonthRange;
import me.seantwiehaus.zbbp.domain.CategoryGroup;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CategoryGroupService {
    private final CategoryGroupRepository repository;

    public CategoryGroupService(CategoryGroupRepository repository) {
        this.repository = repository;
    }

    /**
     * @param budgetMonthRange Range of BudgetMonths to search
     * @return All CategoryGroups with budgetDates between the start and end BudgetDates (inclusive).
     */
    public List<CategoryGroup> getAllCategoryGroupsBetween(BudgetMonthRange budgetMonthRange) {
        if (budgetMonthRange == null) return Collections.emptyList();
        return repository.findAllByBudgetDateBetween(budgetMonthRange.getStart().asLocalDate(),
                        budgetMonthRange.getEnd().asLocalDate())
                .stream()
                .map(CategoryGroupEntity::convertToCategoryGroup)
                .toList();
    }
}
