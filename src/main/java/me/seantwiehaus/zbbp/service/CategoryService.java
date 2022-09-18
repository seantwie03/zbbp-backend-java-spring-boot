package me.seantwiehaus.zbbp.service;

import lombok.extern.slf4j.Slf4j;
import me.seantwiehaus.zbbp.dao.entity.CategoryEntity;
import me.seantwiehaus.zbbp.dao.repository.CategoryRepository;
import me.seantwiehaus.zbbp.domain.BudgetMonthRange;
import me.seantwiehaus.zbbp.domain.Category;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Stream;

@Slf4j
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
    public Stream<Category> getAllCategoriesBetween(BudgetMonthRange budgetMonthRange) {
        if (budgetMonthRange == null) return Stream.<Category>builder().build();
        return repository.findAllByBudgetDateBetween(budgetMonthRange.getStart().asLocalDate(),
                        budgetMonthRange.getEnd().asLocalDate())
                .stream()
                .map(CategoryEntity::convertToCategory);
    }

    public Optional<Category> findCategoryById(Long id) {
        if (id == null) return Optional.empty();
        return repository.findCategoryEntityById(id)
                .map(CategoryEntity::convertToCategory);
    }

    public Category create(Category category) {
        log.info("Creating new transaction -> " + category);
        return repository.save(new CategoryEntity(category)).convertToCategory();
    }
}
