package me.seantwiehaus.zbbp.service;

import me.seantwiehaus.zbbp.dao.entity.CategoryGroupEntity;
import me.seantwiehaus.zbbp.dao.repository.CategoryGroupRepository;
import me.seantwiehaus.zbbp.domain.CategoryGroup;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CategoryGroupService {
    private final CategoryGroupRepository repository;

    public CategoryGroupService(CategoryGroupRepository repository) {
        this.repository = repository;
    }

    /**
     * @param startBudgetDate Include all CategoryGroups with budgetDates greater-than-or-equal to this budgetDate.
     *                        BudgetDates are always on the 1st day of the month.
     *                        If no value is supplied, the default value will be the first day of the current month
     *                        100 years in the past.
     * @param endBudgetDate   Include all CategoryGroups with budgetDates less-than-or-equal-to this budgetDate.
     *                        BudgetDates are always on the 1st day of the month.
     *                        If no value is supplied, the default value will be the first day of the current month
     *                        100 years in the future.
     * @return All CategoryGroups with budgetDates between the start and end budgetDates (inclusive).
     */
    public List<CategoryGroup> getAllCategoryGroupsBetween(LocalDate startBudgetDate, LocalDate endBudgetDate) {
        return repository.findAllByBudgetDateBetween(startBudgetDate, endBudgetDate).stream()
                .map(CategoryGroupEntity::convertToCategoryGroup)
                .toList();
    }
}
