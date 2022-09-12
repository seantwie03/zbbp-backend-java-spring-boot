package me.seantwiehaus.zbbp.service;

import me.seantwiehaus.zbbp.dao.entity.CategoryEntity;
import me.seantwiehaus.zbbp.dao.repository.CategoryRepository;
import me.seantwiehaus.zbbp.domain.BudgetDate;
import me.seantwiehaus.zbbp.domain.Category;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    /**
     * @param start Include all Categories with budgetDates greater-than-or-equal to this BudgetDate.
     * @param end   Include all Categories with budgetDates less-than-or-equal-to this BudgetDate.
     * @return All Categories with budgetDates between the start and end BudgetDates (inclusive).
     */
    public List<Category> getAllCategoriesBetween(@NotNull BudgetDate start, @NotNull BudgetDate end) {
        return repository.findAllByBudgetDateBetween(start.asLocalDate(), end.asLocalDate())
                .stream()
                .map(CategoryEntity::convertToCategory)
                .toList();
    }

    public Optional<Category> findCategoryById(@NotNull Long id) {
        return repository.findCategoryEntityById(id)
                .map(CategoryEntity::convertToCategory);
    }
}
