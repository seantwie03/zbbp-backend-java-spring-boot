package me.seantwiehaus.zbbp.dto.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import me.seantwiehaus.zbbp.domain.Budget;
import me.seantwiehaus.zbbp.dto.serialize.CentsToDollarsSerializer;

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

  @JsonSerialize(using = CentsToDollarsSerializer.class)
  private final int totalPlannedIncome;
  @JsonSerialize(using = CentsToDollarsSerializer.class)
  private final int totalPlannedExpense;
  @JsonSerialize(using = CentsToDollarsSerializer.class)
  private final int totalLeftToBudget;
  @JsonSerialize(using = CentsToDollarsSerializer.class)
  private final int totalSpent;
  @JsonSerialize(using = CentsToDollarsSerializer.class)
  private final int totalLeftToSpend;

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
    this.totalPlannedIncome = budget.getTotalPlannedIncome();
    this.totalPlannedExpense = budget.getTotalPlannedExpense();
    this.totalLeftToBudget = budget.getTotalLeftToBudget();
    this.totalSpent = budget.getTotalSpent();
    this.totalLeftToSpend = budget.getTotalLeftToSpend();
  }
}
