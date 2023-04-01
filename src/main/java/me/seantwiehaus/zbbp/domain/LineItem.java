package me.seantwiehaus.zbbp.domain;

import lombok.Builder;
import me.seantwiehaus.zbbp.dto.response.DollarsToCentsConverter;

import java.time.Instant;
import java.time.YearMonth;
import java.util.List;

/**
 * @param transactions Unmodifiable List
 */
@Builder
public record LineItem(
        Long id,
        YearMonth budgetDate,
        String name,
        int plannedAmount,
        Category category,
        String description,
        Instant lastModifiedAt,
        List<Transaction> transactions) {
  public LineItem {
    transactions = transactions != null ? List.copyOf(transactions) : List.of();
  }

  public static LineItemBuilder builder(YearMonth budgetDate, String name, int plannedAmount, Category category) {
    return new LineItemBuilder().budgetDate(budgetDate).name(name).plannedAmount(plannedAmount).category(category);
  }

  /**
   * Additional builder to convert plannedAmount from dollars to cents
   */
  public static LineItemBuilder builder(YearMonth budgetDate, String name, double plannedAmount, Category category) {
    return new LineItemBuilder()
            .budgetDate(budgetDate)
            .name(name)
            .plannedAmount(DollarsToCentsConverter.convert(plannedAmount))
            .category(category);
  }

  /**
   * This is the total amount spent or received for this line item.
   * Spent if this is an expense, received if this is an income.
   */
  public int calculateTotalTransactions() {
    return transactions
            .stream()
            .mapToInt(Transaction::amount)
            .sum();
  }

  public double calculatePercentageOfPlanned() {
    // Without this check, plannedAmount could be 0 in the divisor below.
    if (plannedAmount == 0) {
      // If plannedAmount is 0, but totalTransactions is 5.50(converted to dollars) the percentage should be %105.50
      return 100.0 + calculateTotalTransactions() * 100;
    }
    return calculateTotalTransactions() * 100.0 / plannedAmount;
  }

  /**
   * This is the difference between the planned amount and the total transactions.
   * If this amount is positive, then it shows how much is left to transact in order to meet the planned amount.
   * If this amount is negative, then it shows how much less should've been transacted to stay within the planned amount.
   */
  public int calculateTotalRemaining() {
    return plannedAmount - calculateTotalTransactions();
  }
}
