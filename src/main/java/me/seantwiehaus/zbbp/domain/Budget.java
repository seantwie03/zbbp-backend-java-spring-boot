package me.seantwiehaus.zbbp.domain;

import lombok.AccessLevel;
import lombok.Getter;
import me.seantwiehaus.zbbp.exception.InternalServerException;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Budget {
  @Getter(AccessLevel.NONE)
  private final List<LineItem> allIncomeItems;
  @Getter(AccessLevel.NONE)
  private final List<LineItem> allExpenseItems;

  private final YearMonth yearMonth;
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
  private final List<LineItem> utilities;
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
  /**
   * Immutable List
   */
  private final List<LineItem> debts;

  private final int totalPlannedIncome;
  private final int totalPlannedExpense;
  private final int totalLeftToBudget;
  private final int totalSpent;
  private final int totalLeftToSpend;

  public Budget(YearMonth yearMonth, List<LineItem> lineItems) {
    this.yearMonth = yearMonth;

    throwIfNotAllLineItemsHaveCorrectYearMonth(lineItems, yearMonth);

    // Mutable local variables
    List<LineItem> allIncomes = new ArrayList<>();
    List<LineItem> allExpenses = new ArrayList<>();
    List<LineItem> incomeItems = new ArrayList<>();
    List<LineItem> savingItems = new ArrayList<>();
    List<LineItem> investmentItems = new ArrayList<>();
    List<LineItem> housingItems = new ArrayList<>();
    List<LineItem> utilityItems = new ArrayList<>();
    List<LineItem> transportationItems = new ArrayList<>();
    List<LineItem> foodItems = new ArrayList<>();
    List<LineItem> personalItems = new ArrayList<>();
    List<LineItem> healthItems = new ArrayList<>();
    List<LineItem> lifestyleItems = new ArrayList<>();
    List<LineItem> debtItems = new ArrayList<>();

    lineItems.forEach(lineItem -> {
      switch (lineItem.category()) {
        case INCOME -> {
          allIncomes.add(lineItem);
          incomeItems.add(lineItem);
        }
        case SAVINGS -> {
          allExpenses.add(lineItem);
          savingItems.add(lineItem);
        }
        case INVESTMENTS -> {
          allExpenses.add(lineItem);
          investmentItems.add(lineItem);
        }
        case HOUSING -> {
          allExpenses.add(lineItem);
          housingItems.add(lineItem);
        }
        case UTILITIES -> {
          allExpenses.add(lineItem);
          utilityItems.add(lineItem);
        }
        case TRANSPORTATION -> {
          allExpenses.add(lineItem);
          transportationItems.add(lineItem);
        }
        case FOOD -> {
          allExpenses.add(lineItem);
          foodItems.add(lineItem);
        }
        case PERSONAL -> {
          allExpenses.add(lineItem);
          personalItems.add(lineItem);
        }
        case HEALTH -> {
          allExpenses.add(lineItem);
          healthItems.add(lineItem);
        }
        case LIFESTYLE -> {
          allExpenses.add(lineItem);
          lifestyleItems.add(lineItem);
        }
        case DEBTS -> {
          allExpenses.add(lineItem);
          debtItems.add(lineItem);
        }
        default ->
                throw new InternalServerException("LineItem with ID: " + lineItem.id() + " has an invalid Category.");
      }
    });

    // Set fields as immutable lists
    this.allIncomeItems = List.copyOf(allIncomes);
    this.allExpenseItems = List.copyOf(allExpenses);
    this.incomes = List.copyOf(incomeItems);
    this.savings = List.copyOf(savingItems);
    this.investments = List.copyOf(investmentItems);
    this.housing = List.copyOf(housingItems);
    this.utilities = List.copyOf(utilityItems);
    this.transportation = List.copyOf(transportationItems);
    this.food = List.copyOf(foodItems);
    this.personal = List.copyOf(personalItems);
    this.health = List.copyOf(healthItems);
    this.lifestyle = List.copyOf(lifestyleItems);
    this.debts = List.copyOf(debtItems);

    this.totalPlannedIncome = calculateTotalPlannedIncome();
    this.totalPlannedExpense = calculateTotalPlannedExpense();
    this.totalSpent = calculateTotalSpent();
    this.totalLeftToBudget = calculateTotalLeftToBudget();
    this.totalLeftToSpend = calculateTotalLeftToSpend();
  }

  private void throwIfNotAllLineItemsHaveCorrectYearMonth(List<LineItem> lineItems, YearMonth yearMonth) {
    if (lineItems.stream().anyMatch(lineItem -> !lineItem.budgetDate().equals(yearMonth))) {
      throw new InternalServerException("Unable to instantiate a Budget with lineItems from different months");
    }
  }

  private int calculateTotalPlannedIncome() {
    return allIncomeItems
            .stream()
            .mapToInt(LineItem::plannedAmount)
            .sum();
  }

  private int calculateTotalPlannedExpense() {
    return allExpenseItems
            .stream()
            .mapToInt(LineItem::plannedAmount)
            .sum();
  }

  private int calculateTotalSpent() {
    return allExpenseItems
            .stream()
            .mapToInt(LineItem::calculateTotalTransactions)
            .sum();
  }

  private int calculateTotalLeftToBudget() {
    return totalPlannedIncome - totalPlannedExpense;
  }

  private int calculateTotalLeftToSpend() {
    return totalPlannedExpense - totalSpent;
  }
}
