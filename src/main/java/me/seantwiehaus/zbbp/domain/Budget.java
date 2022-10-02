package me.seantwiehaus.zbbp.domain;

import lombok.AccessLevel;
import lombok.Getter;
import me.seantwiehaus.zbbp.exception.InternalServerException;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
public class Budget {
  @Getter(AccessLevel.NONE)
  private final List<LineItem> uncategorized;
  @Getter(AccessLevel.NONE)
  private final List<LineItem> allIncomeItems;
  @Getter(AccessLevel.NONE)
  private final List<LineItem> allExpenseItems;

  private final BudgetMonth budgetMonth;
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

  private final Money totalPlannedIncome;
  private final Money totalPlannedExpense;
  private final Money totalLeftToBudget;
  private final Money totalSpent;
  private final Money totalLeftToSpend;

  public Budget(@NotNull BudgetMonth budgetMonth, @NotNull List<LineItem> lineItems) {
    this.budgetMonth = budgetMonth;
    this.uncategorized = lineItems;

    if (notAllLineItemsHaveCorrectBudgetMonth()) {
      throw new InternalServerException("Unable to instantiate a Budget with lineItems from different months");
    }

    // Mutable local variables
    List<LineItem> allIncomes = new ArrayList<>();
    List<LineItem> allExpenses = new ArrayList<>();
    List<LineItem> incomeItems = new ArrayList<>();
    List<LineItem> savingItems = new ArrayList<>();
    List<LineItem> investmentItems = new ArrayList<>();
    List<LineItem> housingItems = new ArrayList<>();
    List<LineItem> transportationItems = new ArrayList<>();
    List<LineItem> foodItems = new ArrayList<>();
    List<LineItem> personalItems = new ArrayList<>();
    List<LineItem> healthItems = new ArrayList<>();
    List<LineItem> lifestyleItems = new ArrayList<>();

    lineItems.forEach(lineItem -> {
      switch (lineItem.getType()) {
        case INCOME -> allIncomes.add(lineItem);
        case EXPENSE -> allExpenses.add(lineItem);
        default ->
            throw new InternalServerException("LineItem with ID: " + lineItem.getId() + " has an invalid ItemType.");
      }

      switch (lineItem.getCategory()) {
        case INCOME -> incomeItems.add(lineItem);
        case SAVINGS -> savingItems.add(lineItem);
        case INVESTMENTS -> investmentItems.add(lineItem);
        case HOUSING -> housingItems.add(lineItem);
        case TRANSPORTATION -> transportationItems.add(lineItem);
        case FOOD -> foodItems.add(lineItem);
        case PERSONAL -> personalItems.add(lineItem);
        case HEALTH -> healthItems.add(lineItem);
        case LIFESTYLE -> lifestyleItems.add(lineItem);
        default ->
            throw new InternalServerException("LineItem with ID: " + lineItem.getId() + " has an invalid Category.");
      }
    });

    // Set fields as immutable lists
    this.allIncomeItems = Collections.unmodifiableList(allIncomes);
    this.allExpenseItems = Collections.unmodifiableList(allExpenses);
    this.incomes = Collections.unmodifiableList(incomeItems);
    this.savings = Collections.unmodifiableList(savingItems);
    this.investments = Collections.unmodifiableList(investmentItems);
    this.housing = Collections.unmodifiableList(housingItems);
    this.transportation = Collections.unmodifiableList(transportationItems);
    this.food = Collections.unmodifiableList(foodItems);
    this.personal = Collections.unmodifiableList(personalItems);
    this.health = Collections.unmodifiableList(healthItems);
    this.lifestyle = Collections.unmodifiableList(lifestyleItems);

    this.totalPlannedIncome = calculateTotalPlannedIncome();
    this.totalPlannedExpense = calculateTotalPlannedExpense();
    this.totalSpent = calculateTotalSpent();
    this.totalLeftToBudget = calculateTotalLeftToBudget();
    this.totalLeftToSpend = calculateTotalLeftToSpend();
  }

  private boolean notAllLineItemsHaveCorrectBudgetMonth() {
    return uncategorized
        .stream()
        .anyMatch(lineItem -> ! lineItem.getBudgetMonth().equals(budgetMonth));
  }

  private Money calculateTotalPlannedIncome() {
    return new Money(
        allIncomeItems
            .stream()
            .map(LineItem::getPlannedAmount)
            .mapToInt(Money::inCents)
            .sum());
  }

  private Money calculateTotalPlannedExpense() {
    return new Money(
        allIncomeItems
            .stream()
            .filter(lineItem -> lineItem.type.equals(ItemType.INCOME))
            .map(LineItem::getPlannedAmount)
            .mapToInt(Money::inCents)
            .sum());
  }

  private Money calculateTotalSpent() {
    return new Money(
        allExpenseItems
            .stream()
            .filter(lineItem -> lineItem.type.equals(ItemType.EXPENSE))
            .map(LineItem::getTotalTransactions)
            .mapToInt(Money::inCents)
            .sum());
  }

  private Money calculateTotalLeftToBudget() {
    return new Money(totalPlannedIncome.inCents() - totalPlannedExpense.inCents());
  }

  private Money calculateTotalLeftToSpend() {
    return new Money(totalPlannedExpense.inCents() - totalSpent.inCents());
  }

}
