package me.seantwiehaus.zbbp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.seantwiehaus.zbbp.dao.entity.LineItemEntity;
import me.seantwiehaus.zbbp.dao.repository.LineItemRepository;
import me.seantwiehaus.zbbp.domain.Budget;
import me.seantwiehaus.zbbp.domain.LineItem;
import me.seantwiehaus.zbbp.exception.BadRequestException;
import me.seantwiehaus.zbbp.mapper.LineItemMapper;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class BudgetService {
  private final LineItemRepository lineItemRepository;
  private final LineItemMapper mapper;

  /**
   * @param budgetDate The monthly Budget to return
   * @return The Budget for the specified month
   */
  public Budget getForBudgetMonth(YearMonth budgetDate) {
    List<LineItemEntity> entities = lineItemRepository.findAllByBudgetDateOrderByCategoryAscPlannedAmountDesc(budgetDate);
    List<LineItem> lineItems = mapper.mapToDomains(entities);
    return new Budget(budgetDate, lineItems);
  }

  /**
   * This method will attempt to copy the most recent set of LineItems to the budgetMonth specified
   *
   * @param budgetMonth The budgetMonth to copy LineItems to
   * @return The copied budget.
   */
  public Budget create(YearMonth budgetMonth) {
    throwIfLineItemsAlreadyExistForThisBudgetMonth(budgetMonth);
    Optional<YearMonth> mostRecentBudgetDate = findMostRecentBudgetDateWithAtLeastOneLineItem();
    if (mostRecentBudgetDate.isEmpty()) {
      // If no previous budgetMonths have at least one LineItem, this is a completely new user
      return new Budget(budgetMonth, List.of());
    }
    List<LineItemEntity> exiting =
        lineItemRepository.findAllByBudgetDateOrderByCategoryAscPlannedAmountDesc(mostRecentBudgetDate.get());
    List<LineItemEntity> copied = copyLineItemEntitiesToNewBudgetMonth(budgetMonth, exiting);
    copied.forEach(item -> log.info("Creating new Line Item -> %s".formatted(item)));
    List<LineItemEntity> savedItems = lineItemRepository.saveAll(copied);
    List<LineItem> lineItems = mapper.mapToDomains(savedItems);
    return new Budget(budgetMonth, lineItems);
  }

  private void throwIfLineItemsAlreadyExistForThisBudgetMonth(YearMonth budgetMonth) {
    List<LineItemEntity> allByBudgetDate =
        lineItemRepository.findAllByBudgetDateOrderByCategoryAscPlannedAmountDesc(budgetMonth);
    if (!allByBudgetDate.isEmpty()) {
      throw new BadRequestException("Line Items for: %s already exists.".formatted(budgetMonth));
    }
  }

  private Optional<YearMonth> findMostRecentBudgetDateWithAtLeastOneLineItem() {
    Optional<LineItemEntity> topLineItemOptional = lineItemRepository.findTopByOrderByBudgetDateDesc();
    if (topLineItemOptional.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(topLineItemOptional.get().getBudgetDate());
  }

  private List<LineItemEntity> copyLineItemEntitiesToNewBudgetMonth(YearMonth budgetDate,
                                                                    List<LineItemEntity> exitingEntities) {
    return exitingEntities.stream().map(existingEntity -> {
      LineItemEntity newEntity = new LineItemEntity();
      newEntity.setBudgetDate(budgetDate);
      newEntity.setName(existingEntity.getName());
      newEntity.setPlannedAmount(existingEntity.getPlannedAmount());
      newEntity.setCategory(existingEntity.getCategory());
      newEntity.setDescription(existingEntity.getDescription());
      return newEntity;
    }).toList();
  }
}
