package me.seantwiehaus.zbbp.service;

import lombok.extern.slf4j.Slf4j;
import me.seantwiehaus.zbbp.dao.entity.CategoryGroupEntity;
import me.seantwiehaus.zbbp.dao.repository.CategoryGroupRepository;
import me.seantwiehaus.zbbp.domain.BudgetMonthRange;
import me.seantwiehaus.zbbp.domain.CategoryGroup;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
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
  public List<CategoryGroup> getAllBetween(BudgetMonthRange budgetMonthRange) {
    if (budgetMonthRange == null) {
      log.warn("CategoryGroupService::getAllBetween was called with null BudgetMonthRange.");
      return Collections.emptyList();
    }
    return repository.findAllByBudgetDateBetween(
            budgetMonthRange.getStart().asLocalDate(),
            budgetMonthRange.getEnd().asLocalDate())
        .stream()
        .map(CategoryGroupEntity::convertToCategoryGroup)
        .toList();
  }

  public Optional<CategoryGroup> findById(Long id) {
    if (id == null) {
      log.warn("CategoryGroupService::findById was called with null ID");
      return Optional.empty();
    }
    return repository.findById(id)
        .map(CategoryGroupEntity::convertToCategoryGroup);
  }
}
