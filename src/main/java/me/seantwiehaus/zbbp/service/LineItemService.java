package me.seantwiehaus.zbbp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.seantwiehaus.zbbp.dao.entity.LineItemEntity;
import me.seantwiehaus.zbbp.dao.repository.LineItemRepository;
import me.seantwiehaus.zbbp.domain.LineItem;
import me.seantwiehaus.zbbp.exception.PreconditionFailedException;
import me.seantwiehaus.zbbp.exception.ResourceNotFoundException;
import me.seantwiehaus.zbbp.mapper.LineItemMapper;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.YearMonth;
import java.util.List;

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
    return repository.findAllByBudgetDateBetweenOrderByBudgetDateDescCategoryAscPlannedAmountDesc(startBudgetDate, endBudgetDate)
            .stream()
            .map(mapper::mapToDomain)
            .toList();
  }

  public LineItem getById(Long id) {
    LineItemEntity entity = repository.findLineItemEntityById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Line Item", id));
    return mapper.mapToDomain(entity);
  }

  public LineItem create(LineItem lineItem) {
    LineItemEntity entity = mapper.mapToEntity(lineItem);
    log.info("Creating new Line Item -> " + entity);
    LineItemEntity saved = repository.save(entity);
    return mapper.mapToDomain(saved);
  }

  public LineItem update(Long id, Instant ifUnmodifiedSince, LineItem lineItem) {
    LineItemEntity entity = repository.findLineItemEntityById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Line Item", id));
    if (entity.modifiedAfter(ifUnmodifiedSince)) {
      // MDN: If the resource has been modified after the specified date, the response will be a 412 Precondition
      // Failed error. https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/If-Unmodified-Since
      throw new PreconditionFailedException(
              "%s with ID %d was modified after the provided If-Unmodified-Since header value of %s"
                      .formatted(entity.getClass().getSimpleName(), entity.getId(), ifUnmodifiedSince));
    }
    entity.setBudgetDate(lineItem.budgetDate());
    entity.setName(lineItem.name());
    entity.setPlannedAmount(lineItem.plannedAmount());
    entity.setCategory(lineItem.category());
    entity.setDescription(lineItem.description());
    log.info("Updating Line Item with ID=%d -> %s".formatted(id, entity));
    LineItemEntity saved = repository.save(entity);
    return mapper.mapToDomain(saved);
  }

  public void delete(Long id) {
    LineItemEntity entity = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Line Item", id));
    log.info("Deleting Line Item with ID=%d -> %s".formatted(id, entity));
    repository.delete(entity);
  }
}
