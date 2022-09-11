package me.seantwiehaus.zbbp.service;

import me.seantwiehaus.zbbp.dao.entity.CategoryGroupEntity;
import me.seantwiehaus.zbbp.dao.repository.CategoryGroupRepository;
import me.seantwiehaus.zbbp.domain.CategoryGroup;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.List;

@Service
public class CategoryGroupService {
    private final CategoryGroupRepository repository;

    public CategoryGroupService(CategoryGroupRepository repository) {
        this.repository = repository;
    }

    /**
     * @param startBudgetYM Include all CategoryGroups with budgetDates greater-than-or-equal to this YearMonth.
     * @param endBudgetYM   Include all CategoryGroups with budgetDates less-than-or-equal-to this YearMonth.
     * @return All CategoryGroups with budgetDates between the start and end YearMonths (inclusive).
     */
    public List<CategoryGroup> getAllCategoryGroupsBetween(YearMonth startBudgetYM, YearMonth endBudgetYM) {
        return repository.findAllByBudgetDateBetween(startBudgetYM.atDay(1), endBudgetYM.atDay(1)).stream()
                .map(CategoryGroupEntity::convertToCategoryGroup)
                .toList();
    }
}
