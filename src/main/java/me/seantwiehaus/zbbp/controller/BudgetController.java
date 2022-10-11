package me.seantwiehaus.zbbp.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.seantwiehaus.zbbp.dto.response.BudgetResponse;
import me.seantwiehaus.zbbp.service.BudgetService;
import me.seantwiehaus.zbbp.validation.MustBeCurrentOrFutureBudgetDate;
import me.seantwiehaus.zbbp.validation.MustNotBeMoreThanSixMonthsInFuture;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@RestController
@Validated
public class BudgetController {
  private final BudgetService budgetService;

  /**
   * @param budgetDate The date of the budget you are requesting.
   *                   BudgetDates are always on the 1st day of the month.
   *                   If no value is supplied, the default value will be the current month and year
   * @return A Budget for the specified budgetDate.
   */
  @GetMapping("/budget")
  public BudgetResponse getBudgetFor(
      @RequestParam Optional<YearMonth> budgetDate) {
    return new BudgetResponse(budgetService.getForBudgetMonth(budgetDate.orElse(YearMonth.now())));
  }

  /**
   * This endpoint will copy LineItems from the most recent budget to the budgetDate specified.
   *
   * @param budgetDate The budgetDate to copy the LineItems to
   * @return The newly copied budget
   */
  @PostMapping("/budget/{budgetDate}")
  public BudgetResponse createBudgetFor(
      @PathVariable @MustBeCurrentOrFutureBudgetDate @MustNotBeMoreThanSixMonthsInFuture YearMonth budgetDate) {
    return new BudgetResponse(budgetService.create(budgetDate));
  }
}
