package me.seantwiehaus.zbbp.service;

import me.seantwiehaus.zbbp.dao.entity.CategoryEntity;
import me.seantwiehaus.zbbp.dao.repository.CategoryRepository;
import me.seantwiehaus.zbbp.domain.Category;
import me.seantwiehaus.zbbp.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    /**
     * @param startBudgetYM Include all Categories with budgetDates greater-than-or-equal to this YearMonth.
     * @param endBudgetYM   Include all Categories with budgetDates less-than-or-equal-to this YearMonth.
     * @return All Categories with budgetDates between the start and end budgetDates (inclusive).
     */
    public List<Category> getAllCategoriesBetween(YearMonth startBudgetYM, YearMonth endBudgetYM) {
        return repository.findAllByBudgetDateBetween(startBudgetYM.atDay(1), endBudgetYM.atDay(1)).stream()
                .map(CategoryEntity::convertToCategory)
                .toList();
    }

    public Category findCategoryById(Long id) {
        return repository.findCategoryEntityById(id)
                .map(CategoryEntity::convertToCategory)
                .orElseThrow(() -> new NotFoundException("Unable to locate CategoryEntity with ID: " + id));
    }
}
