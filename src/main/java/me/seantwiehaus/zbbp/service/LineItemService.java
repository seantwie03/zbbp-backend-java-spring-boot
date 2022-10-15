package me.seantwiehaus.zbbp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.seantwiehaus.zbbp.dao.entity.LineItemEntity;
import me.seantwiehaus.zbbp.dao.repository.LineItemRepository;
import me.seantwiehaus.zbbp.domain.LineItem;
import me.seantwiehaus.zbbp.exception.NotFoundException;
import me.seantwiehaus.zbbp.exception.ResourceConflictException;
import me.seantwiehaus.zbbp.mapper.LineItemMapper;
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
   * @param startBudgetDate The first budgetDate to include in the list of results.
   * @param endBudgetDate   The last budgetDate to include in the list of results.
   * @return All Line Items between the starting and ending budgetDates (inclusive).
   */
  public List<LineItem> getAllBetween(YearMonth startBudgetDate, YearMonth endBudgetDate) {
    return repository.findAllByBudgetDateBetween(startBudgetDate, endBudgetDate)
        .stream()
        .map(LineItemMapper.INSTANCE::entityToDomain)
        .toList();
  }

  public LineItem findById(Long id) {
    return repository.findLineItemEntityById(id)
        .map(LineItemMapper.INSTANCE::entityToDomain)
        .orElseThrow(() -> new NotFoundException("Line Item", id));
  }

  public LineItem create(LineItem lineItem) {
    LineItemEntity entity = LineItemMapper.INSTANCE.domainToEntity(lineItem);
    log.info("Creating new Line Item -> " + entity);
    LineItemEntity saved = repository.save(entity);
    return LineItemMapper.INSTANCE.entityToDomain(saved);
  }

  public LineItem update(Long id, Instant ifUnmodifiedSince, LineItem lineItem) {
    Optional<LineItemEntity> existingEntity = repository.findLineItemEntityById(id);
    return existingEntity
        .map(entity -> {
          if (entity.getLastModifiedAt().isAfter(ifUnmodifiedSince)) {
            throw new ResourceConflictException(
                "Line Item with ID: " + id + " has been modified since this client requested it.");
          }
          entity.setBudgetDate(lineItem.budgetDate());
          entity.setName(lineItem.name());
          entity.setPlannedAmount(lineItem.plannedAmount());
          entity.setCategory(lineItem.category());
          entity.setDescription(lineItem.description());
          log.info("Updating Line Item with ID=" + id + " -> " + entity);
          LineItemEntity saved = repository.save(entity);
          return LineItemMapper.INSTANCE.entityToDomain(saved);
        })
        .orElseThrow(() -> new NotFoundException("Line Item", id));
  }

  public void delete(Long id) {
    LineItemEntity entity = repository.findById(id).orElseThrow(() -> new NotFoundException("Line Item", id));
    log.info("Deleting Line Item with ID=" + id + " -> " + entity);
    repository.delete(entity);
  }
}
