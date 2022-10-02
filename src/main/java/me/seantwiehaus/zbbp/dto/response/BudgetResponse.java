package me.seantwiehaus.zbbp.dto.response;

import lombok.Getter;
import me.seantwiehaus.zbbp.domain.Budget;
import me.seantwiehaus.zbbp.domain.LineItem;

import java.time.LocalDate;
import java.util.List;

@Getter
public class BudgetResponse {
  /**
   * Day of Month is always set to the 1st
   */
  private final LocalDate budgetDate;
  /**
   * Immutable List
   */
  private final List<LineItem> incomes;
  /**
   * Immutable List
   */
  private final List<LineItem> savings;
  /**
   * Immutable List
   */
  private final List<LineItem> investments;
  /**
   * Immutable List
   */
  private final List<LineItem> housing;
  /**
   * Immutable List
   */
  private final List<LineItem> transportation;
  /**
   * Immutable List
   */
  private final List<LineItem> food;
  /**
   * Immutable List
   */
  private final List<LineItem> personal;
  /**
   * Immutable List
   */
  private final List<LineItem> health;
  /**
   * Immutable List
   */
  private final List<LineItem> lifestyle;

  private final Double totalPlannedIncome;
  private final Double totalPlannedExpense;
  private final Double totalLeftToBudget;
  private final Double totalSpent;
  private final Double totalLeftToSpend;

  public BudgetResponse(Budget budget) {
    this.budgetDate = budget.getBudgetMonth().asLocalDate();
    this.incomes = budget.getIncomes();
    this.savings = budget.getSavings();
    this.investments = budget.getInvestments();
    this.housing = budget.getHousing();
    this.transportation = budget.getTransportation();
    this.food = budget.getFood();
    this.personal = budget.getPersonal();
    this.health = budget.getHealth();
    this.lifestyle = budget.getLifestyle();
    this.totalPlannedIncome = budget.getTotalPlannedIncome().inDollars();
    this.totalPlannedExpense = budget.getTotalPlannedExpense().inDollars();
    this.totalLeftToBudget = budget.getTotalLeftToBudget().inDollars();
    this.totalSpent = budget.getTotalSpent().inDollars();
    this.totalLeftToSpend = budget.getTotalLeftToSpend().inDollars();
  }
}
