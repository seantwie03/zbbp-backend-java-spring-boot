package me.seantwiehaus.zbbp.dto.response;

import lombok.Getter;
import me.seantwiehaus.zbbp.domain.Budget;

import java.time.YearMonth;
import java.util.List;

@Getter
public class BudgetResponse {
  private final YearMonth budgetDate;
  /**
   * Immutable List
   */
  private final List<LineItemResponse> incomes;
  /**
   * Immutable List
   */
  private final List<LineItemResponse> savings;
  /**
   * Immutable List
   */
  private final List<LineItemResponse> investments;
  /**
   * Immutable List
   */
  private final List<LineItemResponse> housing;
  /**
   * Immutable List
   */
  private final List<LineItemResponse> transportation;
  /**
   * Immutable List
   */
  private final List<LineItemResponse> food;
  /**
   * Immutable List
   */
  private final List<LineItemResponse> personal;
  /**
   * Immutable List
   */
  private final List<LineItemResponse> health;
  /**
   * Immutable List
   */
  private final List<LineItemResponse> lifestyle;

  private final Double totalPlannedIncome;
  private final Double totalPlannedExpense;
  private final Double totalLeftToBudget;
  private final Double totalSpent;
  private final Double totalLeftToSpend;

  public BudgetResponse(Budget budget) {
    this.budgetDate = budget.getBudgetDate();
    this.incomes = budget.getIncomes()
        .stream()
        .map(LineItemResponse::new)
        .toList();
    this.savings = budget.getSavings()
        .stream()
        .map(LineItemResponse::new)
        .toList();
    this.investments = budget.getInvestments()
        .stream()
        .map(LineItemResponse::new)
        .toList();
    this.housing = budget.getHousing()
        .stream()
        .map(LineItemResponse::new)
        .toList();
    this.transportation = budget.getTransportation()
        .stream()
        .map(LineItemResponse::new)
        .toList();
    this.food = budget.getFood()
        .stream()
        .map(LineItemResponse::new)
        .toList();
    this.personal = budget.getPersonal()
        .stream()
        .map(LineItemResponse::new)
        .toList();
    this.health = budget.getHealth()
        .stream()
        .map(LineItemResponse::new)
        .toList();
    this.lifestyle = budget.getLifestyle()
        .stream()
        .map(LineItemResponse::new)
        .toList();
    this.totalPlannedIncome = budget.getTotalPlannedIncome().inDollars();
    this.totalPlannedExpense = budget.getTotalPlannedExpense().inDollars();
    this.totalLeftToBudget = budget.getTotalLeftToBudget().inDollars();
    this.totalSpent = budget.getTotalSpent().inDollars();
    this.totalLeftToSpend = budget.getTotalLeftToSpend().inDollars();
  }
}
