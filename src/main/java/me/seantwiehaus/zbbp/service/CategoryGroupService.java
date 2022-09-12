package me.seantwiehaus.zbbp.service;

import me.seantwiehaus.zbbp.dao.entity.CategoryGroupEntity;
import me.seantwiehaus.zbbp.dao.repository.CategoryGroupRepository;
import me.seantwiehaus.zbbp.domain.BudgetDate;
import me.seantwiehaus.zbbp.domain.CategoryGroup;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;

@Service
public class CategoryGroupService {
    private final CategoryGroupRepository repository;

    public CategoryGroupService(CategoryGroupRepository repository) {
        this.repository = repository;
    }

    /**
     * @param start Include all CategoryGroups with budgetDates greater-than-or-equal to this BudgetDate.
     * @param end   Include all CategoryGroups with budgetDates less-than-or-equal-to this BudgetDate.
     * @return All CategoryGroups with budgetDates between the start and end BudgetDates (inclusive).
     */
    public List<CategoryGroup> getAllCategoryGroupsBetween(@NotNull BudgetDate start, @NotNull BudgetDate end) {
        return repository.findAllByBudgetDateBetween(start.asLocalDate(), end.asLocalDate())
                .stream()
                .map(CategoryGroupEntity::convertToCategoryGroup)
                .toList();
    }
}
