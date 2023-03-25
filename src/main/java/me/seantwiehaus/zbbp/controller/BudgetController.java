package me.seantwiehaus.zbbp.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.seantwiehaus.zbbp.domain.Budget;
import me.seantwiehaus.zbbp.dto.response.BudgetResponse;
import me.seantwiehaus.zbbp.mapper.BudgetMapper;
import me.seantwiehaus.zbbp.service.BudgetService;
import me.seantwiehaus.zbbp.validation.MustBeCurrentOrFutureBudgetDate;
import me.seantwiehaus.zbbp.validation.MustNotBeMoreThanSixMonthsInFuture;
import org.springframework.format.annotation.DateTimeFormat;
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
  private final BudgetMapper mapper;

  /**
   * @param budgetYearMonth The Year and Month of the budget you are requesting.
   *                        Format: yyyy-M (2023-1) or yyyy-MM (2023-01)
   *                        The default value is the current year-month.
   * @return A Budget for the specified year and month.
   */
  @GetMapping("/budgets")
  public BudgetResponse getBudget(
          @RequestParam @DateTimeFormat(pattern = "yyyy-M") Optional<YearMonth> budgetYearMonth) {
    Budget budget = budgetService.getFor(budgetYearMonth.orElse(YearMonth.now()));
    return mapper.mapToResponse(budget);
  }

  /**
   * This endpoint will copy LineItems from the most recent budget to the budgetDate specified.
   *
   * @param budgetDate The budgetDate to copy the LineItems to
   * @return The newly copied budget
   */
  @PostMapping("/budgets/{budgetDate}")
  public BudgetResponse createBudgetFor(
          @PathVariable @MustBeCurrentOrFutureBudgetDate @MustNotBeMoreThanSixMonthsInFuture YearMonth budgetDate) {
    Budget budget = budgetService.createFor(budgetDate);
    return mapper.mapToResponse(budget);
  }
}
