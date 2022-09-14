package me.seantwiehaus.zbbp.service;

import me.seantwiehaus.zbbp.dao.entity.CategoryEntity;
import me.seantwiehaus.zbbp.dao.repository.CategoryRepository;
import me.seantwiehaus.zbbp.domain.BudgetMonthRange;
import me.seantwiehaus.zbbp.domain.Category;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    /**
     * @param budgetMonthRange Range of BudgetMonths to search
     * @return All Categories with budgetDates between the start and end BudgetDates (inclusive).
     */
    public List<Category> getAllCategoriesBetween(BudgetMonthRange budgetMonthRange) {
        if (budgetMonthRange == null) return Collections.emptyList();
        return repository.findAllByBudgetDateBetween(budgetMonthRange.getStart().asLocalDate(),
                        budgetMonthRange.getEnd().asLocalDate())
                .stream()
                .map(CategoryEntity::convertToCategory)
                .toList();
    }

    public Optional<Category> findCategoryById(Long id) {
        if (id == null) return Optional.empty();
        return repository.findCategoryEntityById(id)
                .map(CategoryEntity::convertToCategory);
    }
}
