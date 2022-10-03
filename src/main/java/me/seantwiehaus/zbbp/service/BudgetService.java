package me.seantwiehaus.zbbp.service;

import lombok.extern.slf4j.Slf4j;
import me.seantwiehaus.zbbp.dao.entity.LineItemEntity;
import me.seantwiehaus.zbbp.dao.repository.LineItemRepository;
import me.seantwiehaus.zbbp.domain.Budget;
import me.seantwiehaus.zbbp.domain.BudgetMonth;
import me.seantwiehaus.zbbp.domain.LineItem;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class BudgetService {
  private final LineItemRepository lineItemRepository;

  public BudgetService(LineItemRepository lineItemRepository) {
    this.lineItemRepository = lineItemRepository;
  }

  /**
   * @param budgetMonth The monthly Budget to return
   * @return The Budget for the specified month
   */
  public Budget getForBudgetMonth(BudgetMonth budgetMonth) {
    if (budgetMonth == null) {
      log.warn("BudgetService::getForBudgetMonth was called with null BudgetMonth.");
      budgetMonth = new BudgetMonth();
    }
    List<LineItem> lineItems = lineItemRepository.findAllByBudgetDate(budgetMonth.asLocalDate())
        .stream()
        .map(LineItemEntity::convertToLineItem)
        .toList();
    return new Budget(budgetMonth, lineItems);
  }
}
