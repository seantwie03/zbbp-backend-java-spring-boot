package me.seantwiehaus.zbbp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.seantwiehaus.zbbp.dao.entity.LineItemEntity;
import me.seantwiehaus.zbbp.dao.repository.LineItemRepository;
import me.seantwiehaus.zbbp.domain.LineItem;
import me.seantwiehaus.zbbp.exception.ResourceNotFoundException;
import me.seantwiehaus.zbbp.mapper.LineItemMapper;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.YearMonth;
import java.util.List;

import static me.seantwiehaus.zbbp.validation.IfUnmodifiedSinceValidation.throwWhenEntityLastModifiedAtIsAfterIfUnmodifiedSince;

@RequiredArgsConstructor
@Slf4j
@Service
public class LineItemService {
  private final LineItemRepository repository;
  private final LineItemMapper mapper;

  /**
   * @param startBudgetDate The first budgetDate to include in the list of results.
   * @param endBudgetDate   The last budgetDate to include in the list of results.
   * @return All Line Items between the starting and ending budgetDates (inclusive).
   */
  public List<LineItem> getAllBetween(YearMonth startBudgetDate, YearMonth endBudgetDate) {
    return repository.findAllByBudgetDateBetween(startBudgetDate, endBudgetDate)
        .stream()
        .map(mapper::mapEntityToDomain)
        .toList();
  }

  public LineItem findById(Long id) {
    return repository.findLineItemEntityById(id)
        .map(mapper::mapEntityToDomain)
        .orElseThrow(() -> new ResourceNotFoundException("Line Item", id));
  }

  public LineItem create(LineItem lineItem) {
    LineItemEntity entity = mapper.mapDomainToEntity(lineItem);
    log.info("Creating new Line Item -> " + entity);
    LineItemEntity saved = repository.save(entity);
    return mapper.mapEntityToDomain(saved);
  }

  public LineItem update(Long id, Instant ifUnmodifiedSince, LineItem lineItem) {
    LineItemEntity entity = repository.findLineItemEntityById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Line Item", id));
    throwWhenEntityLastModifiedAtIsAfterIfUnmodifiedSince(entity, ifUnmodifiedSince);
    entity.setBudgetDate(lineItem.budgetDate());
    entity.setName(lineItem.name());
    entity.setPlannedAmount(lineItem.plannedAmount());
    entity.setCategory(lineItem.category());
    entity.setDescription(lineItem.description());
    log.info("Updating Line Item with ID=%d -> %s".formatted(id, entity));
    LineItemEntity saved = repository.save(entity);
    return mapper.mapEntityToDomain(saved);
  }

  public void delete(Long id) {
    LineItemEntity entity = repository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Line Item", id));
    log.info("Deleting Line Item with ID=%d -> %s".formatted(id, entity));
    repository.delete(entity);
  }
}
