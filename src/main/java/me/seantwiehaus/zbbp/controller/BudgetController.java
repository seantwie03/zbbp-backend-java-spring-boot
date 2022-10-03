package me.seantwiehaus.zbbp.controller;

import lombok.extern.slf4j.Slf4j;
import me.seantwiehaus.zbbp.domain.BudgetMonth;
import me.seantwiehaus.zbbp.dto.response.BudgetResponse;
import me.seantwiehaus.zbbp.service.BudgetService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@Slf4j
@RestController
public class BudgetController {
  BudgetService budgetService;

  public BudgetController(BudgetService budgetService) {
    this.budgetService = budgetService;
  }

  /**
   * @param budgetDate The date of the budget you are requesting.
   *                   BudgetDates are always on the 1st day of the month.
   *                   If no value is supplied, the default value will be the current month and year
   * @return A Budget for the specified budgetDate.
   */
  @GetMapping("/budget")
  public BudgetResponse getBudgetFor(
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> budgetDate) {
    BudgetMonth budgetMonth = budgetDate.map(BudgetMonth::new).orElse(new BudgetMonth());
    return new BudgetResponse(budgetService.getForBudgetMonth(budgetMonth));
  }

  /**
   * This endpoint will copy LineItems from the most recent budget to the budgetDate specified.
   *
   * @param budgetDate The budgetDate to copy the LineItems to
   * @return The newly copied budget
   */
  @PostMapping("/budget/{budgetDate}")
  public BudgetResponse createBudgetFor(
      @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate budgetDate) {
    return new BudgetResponse(budgetService.create(new BudgetMonth(budgetDate)));
  }
}
