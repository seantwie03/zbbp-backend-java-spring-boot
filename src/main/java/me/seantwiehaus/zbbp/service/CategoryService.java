package me.seantwiehaus.zbbp.service;

import me.seantwiehaus.zbbp.dao.entity.CategoryEntity;
import me.seantwiehaus.zbbp.dao.repository.CategoryRepository;
import me.seantwiehaus.zbbp.domain.Category;
import me.seantwiehaus.zbbp.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    /**
     * @param startBudgetDate Include all Categories with budgetDates greater-than-or-equal to this budgetDate.
     *                        BudgetDates are always on the 1st day of the month.
     *                        If no value is supplied, the default value will be the first day of the current month
     *                        100 years in the past.
     * @param endBudgetDate   Include all Categories with budgetDates less-than-or-equal-to this budgetDate.
     *                        BudgetDates are always on the 1st day of the month.
     *                        If no value is supplied, the default value will be the first day of the current month
     *                        100 years in the future.
     * @return All Categories with budgetDates between the start and end budgetDates (inclusive).
     */
    public List<Category> getAllCategoriesBetween(LocalDate startBudgetDate, LocalDate endBudgetDate) {
        return repository.findAllByBudgetDateBetween(startBudgetDate, endBudgetDate).stream()
                .map(CategoryEntity::convertToCategory)
                .toList();
    }

    public Category findCategoryById(Long id) {
        return repository.findCategoryEntityById(id)
                .map(CategoryEntity::convertToCategory)
                .orElseThrow(() -> new NotFoundException("Unable to locate CategoryEntity with ID: " + id));
    }
}
