package me.seantwiehaus.zbbp.domain;

import lombok.AccessLevel;
import lombok.Getter;

import java.util.List;

@Getter
public class Budget {
  @Getter(AccessLevel.NONE)
  private final List<LineItem> incomeItems;
  @Getter(AccessLevel.NONE)
  private final List<LineItem> expenseItems;
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

  public Budget(List<LineItem> lineItems) {
    this.incomeItems = lineItems
        .stream()
        .filter(lineItem -> lineItem.getType().equals(ItemType.INCOME))
        .toList();
    this.expenseItems = lineItems
        .stream()
        .filter(lineItem -> lineItem.getType().equals(ItemType.EXPENSE))
        .toList();

    this.incomes = lineItems
        .stream()
        .filter(lineItem -> lineItem.getCategory().equals(Category.INCOME))
        .toList();
    this.savings = lineItems
        .stream()
        .filter(lineItem -> lineItem.getCategory().equals(Category.SAVINGS))
        .toList();
    this.investments = lineItems
        .stream()
        .filter(lineItem -> lineItem.getCategory().equals(Category.INVESTMENTS))
        .toList();
    this.housing = lineItems
        .stream()
        .filter(lineItem -> lineItem.getCategory().equals(Category.HOUSING))
        .toList();
    this.transportation = lineItems
        .stream()
        .filter(lineItem -> lineItem.getCategory().equals(Category.TRANSPORTATION))
        .toList();
    this.food = lineItems
        .stream()
        .filter(lineItem -> lineItem.getCategory().equals(Category.FOOD))
        .toList();
    this.personal = lineItems
        .stream()
        .filter(lineItem -> lineItem.getCategory().equals(Category.PERSONAL))
        .toList();
    this.health = lineItems
        .stream()
        .filter(lineItem -> lineItem.getCategory().equals(Category.HEALTH))
        .toList();
    this.lifestyle = lineItems
        .stream()
        .filter(lineItem -> lineItem.getCategory().equals(Category.LIFESTYLE))
        .toList();

    this.totalPlannedIncome = calculateTotalPlannedIncome();
    this.totalPlannedExpense = calculateTotalPlannedExpense();
    this.totalSpent = calculateTotalSpent();
    this.totalLeftToBudget = calculateTotalLeftToBudget();
    this.totalLeftToSpend = calculateTotalLeftToSpend();
  }

  private Money calculateTotalPlannedIncome() {
    return new Money(
        incomeItems
            .stream()
            .map(LineItem::getPlannedAmount)
            .mapToInt(Money::inCents)
            .sum());
  }

  private Money calculateTotalPlannedExpense() {
    return new Money(
        incomeItems
            .stream()
            .filter(lineItem -> lineItem.type.equals(ItemType.INCOME))
            .map(LineItem::getPlannedAmount)
            .mapToInt(Money::inCents)
            .sum());
  }

  private Money calculateTotalSpent() {
    return new Money(
        expenseItems
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
