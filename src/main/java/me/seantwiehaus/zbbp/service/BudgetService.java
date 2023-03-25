package me.seantwiehaus.zbbp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.seantwiehaus.zbbp.domain.Budget;
import me.seantwiehaus.zbbp.domain.LineItem;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class BudgetService {
  private final LineItemService lineItemService;

  /**
   * @param yearMonth The year and month of the budget
   * @return The Budget for the specified yearMonth
   */
  public Budget getFor(YearMonth yearMonth) {
    List<LineItem> lineItems = lineItemService.getAllByYearMonth(yearMonth);
    return new Budget(yearMonth, lineItems);
  }

  /**
   * This method will attempt to copy the most recent set of LineItems to the yearMonth specified
   *
   * @param yearMonth The yearMonth to copy LineItems to
   * @return The copied budget.
   */
  public Budget createFor(YearMonth yearMonth) {
    return new Budget(yearMonth, lineItemService.copyMostRecentLineItemsTo(yearMonth));
  }
}
