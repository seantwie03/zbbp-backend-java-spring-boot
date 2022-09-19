package me.seantwiehaus.zbbp.service;

import lombok.extern.slf4j.Slf4j;
import me.seantwiehaus.zbbp.dao.entity.CategoryEntity;
import me.seantwiehaus.zbbp.dao.repository.CategoryRepository;
import me.seantwiehaus.zbbp.domain.BudgetMonthRange;
import me.seantwiehaus.zbbp.domain.Category;
import me.seantwiehaus.zbbp.exception.InternalServerException;
import me.seantwiehaus.zbbp.exception.ResourceConflictException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CategoryService {
    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    /**
     * @param budgetMonthRange Range of BudgetMonths to search
     * @return All Categories within budgetMonthRange (inclusive).
     */
    public List<Category> getAllBetween(BudgetMonthRange budgetMonthRange) {
        if (budgetMonthRange == null) {
            log.warn("CategoryService::getAllBetween was called with null BudgetMonthRange.");
            return Collections.emptyList();
        }
        return repository.findAllByBudgetDateBetween(
                        budgetMonthRange.getStart().asLocalDate(), budgetMonthRange.getEnd().asLocalDate())
                .stream()
                .map(CategoryEntity::convertToCategory)
                .toList();
    }

    public Optional<Category> findById(Long id) {
        if (id == null) {
            log.warn("CategoryService::findById was called with null ID");
            return Optional.empty();
        }
        return repository.findCategoryEntityById(id)
                .map(CategoryEntity::convertToCategory);
    }

    public Category create(Category category) {
        log.info("Creating new Category -> " + category);
        return repository.save(new CategoryEntity(category)).convertToCategory();
    }

    public Optional<Category> update(Long id, Instant ifUnmodifiedSince, Category category) {
        if (id == null || ifUnmodifiedSince == null || category == null) {
            throw new InternalServerException("Unable to update Category. One or more parameters is null");
        }
        Optional<CategoryEntity> existingEntity = repository.findById(id);
        return existingEntity
                .map(entity -> {
                    if (entity.getLastModifiedAt().isAfter(ifUnmodifiedSince)) {
                        throw new ResourceConflictException(
                                "Category with ID: " + id + " has been modified since this client requested it.");
                    }
                    entity.setName(category.getName());
                    entity.setCategoryGroupId(category.getCategoryGroupId());
                    entity.setPlannedAmount(category.getPlannedAmount().inCents());
                    entity.setBudgetDate(category.getBudgetMonth());
                    log.info("Updating Category with ID=" + id + " -> " + entity);
                    return Optional.of(repository.save(entity).convertToCategory());

                })
                .orElse(Optional.empty());
    }
}
