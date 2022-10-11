package me.seantwiehaus.zbbp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.seantwiehaus.zbbp.dao.entity.LineItemEntity;
import me.seantwiehaus.zbbp.dao.repository.LineItemRepository;
import me.seantwiehaus.zbbp.domain.LineItem;
import me.seantwiehaus.zbbp.exception.ResourceConflictException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class LineItemService {
  private final LineItemRepository repository;

  /**
   * @param startingBudgetDate The first budgetDate to include in the list of results.
   * @param endingBudgetDate   The last budgetDate to include in the list of results.
   * @return All Line Items between starting and ending budgetDates (inclusive).
   */
  public List<LineItem> getAllBetween(YearMonth startingBudgetDate, YearMonth endingBudgetDate) {
    return repository.findAllByBudgetDateBetween(startingBudgetDate, endingBudgetDate)
        .stream()
        .map(LineItemEntity::convertToLineItem)
        .toList();
  }

  public Optional<LineItem> findById(Long id) {
    return repository.findLineItemEntityById(id)
        .map(LineItemEntity::convertToLineItem);
  }

  public LineItem create(LineItem lineItem) {
    log.info("Creating new Line Item -> " + lineItem);
    return repository.save(new LineItemEntity(lineItem)).convertToLineItem();
  }

  public Optional<LineItem> update(Long id, Instant ifUnmodifiedSince, LineItem lineItem) {
    Optional<LineItemEntity> existingEntity = repository.findLineItemEntityById(id);
    return existingEntity
        .map(entity -> {
          if (entity.getLastModifiedAt().isAfter(ifUnmodifiedSince)) {
            throw new ResourceConflictException(
                "Line Item with ID: " + id + " has been modified since this client requested it.");
          }
          entity.setBudgetDate(lineItem.getBudgetDate());
          entity.setName(lineItem.getName());
          entity.setPlannedAmount(lineItem.getPlannedAmount());
          entity.setCategory(lineItem.getCategory());
          entity.setDescription(lineItem.getDescription());
          log.info("Updating Line Item with ID=" + id + " -> " + entity);
          return Optional.of(repository.save(entity).convertToLineItem());
        })
        .orElse(Optional.empty());
  }

  public Optional<Long> delete(Long id) {
    return repository.findById(id)
        .map(entity -> {
          log.info("Deleting Line Item with ID=" + id + " -> " + entity);
          repository.delete(entity);
          return id;
        });
  }
}
