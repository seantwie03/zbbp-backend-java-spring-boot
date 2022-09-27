package me.seantwiehaus.zbbp.service;

import lombok.extern.slf4j.Slf4j;
import me.seantwiehaus.zbbp.dao.entity.LineItemEntity;
import me.seantwiehaus.zbbp.dao.repository.LineItemRepository;
import me.seantwiehaus.zbbp.domain.BudgetMonthRange;
import me.seantwiehaus.zbbp.domain.Category;
import me.seantwiehaus.zbbp.domain.LineItem;
import me.seantwiehaus.zbbp.exception.BadRequestException;
import me.seantwiehaus.zbbp.exception.InternalServerException;
import me.seantwiehaus.zbbp.exception.ResourceConflictException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class LineItemService {
  private final LineItemRepository repository;
  private final CategoryService categoryService;

  public LineItemService(LineItemRepository repository, CategoryService categoryService) {
    this.repository = repository;
    this.categoryService = categoryService;
  }

  /**
   * @param budgetMonthRange Range of BudgetMonths to search
   * @return All Line Items within budgetMonthRange (inclusive).
   */
  public List<LineItem> getAllBetween(BudgetMonthRange budgetMonthRange) {
    if (budgetMonthRange == null) {
      log.warn("LineItemService::getAllBetween was called with null BudgetMonthRange.");
      return Collections.emptyList();
    }
    return repository.findAllByBudgetDateBetween(
            budgetMonthRange.getStart().asLocalDate(),
            budgetMonthRange.getEnd().asLocalDate())
        .stream()
        .map(LineItemEntity::convertToLineItem)
        .toList();
  }

  public Optional<LineItem> findById(Long id) {
    if (id == null) {
      log.warn("LineItemService::findById was called with null ID");
      return Optional.empty();
    }
    return repository.findLineItemEntityById(id)
        .map(LineItemEntity::convertToLineItem);
  }

  public LineItem create(LineItem lineItem) {
    log.info("Creating new Line Item -> " + lineItem);
    throwIfCategoryDoesNotExistByIdOrHasDifferentType(lineItem);
    return repository.save(new LineItemEntity(lineItem)).convertToLineItem();
  }

  private void throwIfCategoryDoesNotExistByIdOrHasDifferentType(LineItem lineItem) {
    Optional<Category> categoryOptional = categoryService.findById(lineItem.getCategoryId());
    Category category = categoryOptional.orElseThrow(
        () -> new BadRequestException("Unable to find Category with ID: " + lineItem.getCategoryId()));
    if (category.getType() != lineItem.getType()) {
      throw new BadRequestException("Unable to add Line Item with type: " + lineItem.getType().toString() +
          " to Category with type: " + category.getType().toString());
    }
  }

  public Optional<LineItem> update(Long id, Instant ifUnmodifiedSince, LineItem lineItem) {
    if (id == null || ifUnmodifiedSince == null || lineItem == null) {
      throw new InternalServerException("Unable to update Line Item. One or more parameters is null");
    }
    Optional<LineItemEntity> existingEntity = repository.findLineItemEntityById(id);
    return existingEntity
        .map(entity -> {
          if (entity.getLastModifiedAt().isAfter(ifUnmodifiedSince)) {
            throw new ResourceConflictException(
                "Line Item with ID: " + id + " has been modified since this client requested it.");
          }
          entity.setBudgetDate(lineItem.getBudgetMonth());
          entity.setName(lineItem.getName());
          entity.setPlannedAmount(lineItem.getPlannedAmount().inCents());
          throwIfCategoryDoesNotExistByIdOrHasDifferentType(lineItem);
          entity.setCategoryId(lineItem.getCategoryId());
          entity.setDescription(lineItem.getDescription());
          log.info("Updating Line Item with ID=" + id + " -> " + entity);
          return Optional.of(repository.save(entity).convertToLineItem());
        })
        .orElse(Optional.empty());
  }

  public Optional<Long> delete(Long id) {
    if (id == null) throw new InternalServerException("Unable to delete Line Item. ID is null");
    return repository.findById(id)
        .map(entity -> {
          log.info("Deleting Line Item with ID=" + id + " -> " + entity);
          repository.delete(entity);
          return id;
        });
  }
}
