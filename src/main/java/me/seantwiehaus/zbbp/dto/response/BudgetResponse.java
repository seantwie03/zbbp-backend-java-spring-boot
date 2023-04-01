package me.seantwiehaus.zbbp.dto.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import me.seantwiehaus.zbbp.dto.serializer.TwoDecimalPlacesSerializer;

import java.time.YearMonth;
import java.util.List;

public record BudgetResponse(
        YearMonth budgetDate,
        List<LineItemResponse> incomes,
        List<LineItemResponse> savings,
        List<LineItemResponse> investments,
        List<LineItemResponse> housing,
        List<LineItemResponse> utilities,
        List<LineItemResponse> transportation,
        List<LineItemResponse> food,
        List<LineItemResponse> personal,
        List<LineItemResponse> health,
        List<LineItemResponse> lifestyle,
        List<LineItemResponse> debts,

        @JsonSerialize(using = TwoDecimalPlacesSerializer.class) Double totalPlannedIncome,
        @JsonSerialize(using = TwoDecimalPlacesSerializer.class) Double totalReceived,
        @JsonSerialize(using = TwoDecimalPlacesSerializer.class) Double totalPlannedExpense,
        @JsonSerialize(using = TwoDecimalPlacesSerializer.class) Double totalSpent,
        @JsonSerialize(using = TwoDecimalPlacesSerializer.class) Double totalRemaining,
        @JsonSerialize(using = TwoDecimalPlacesSerializer.class) Double totalLeftToBudget
) {
  public BudgetResponse {
    incomes = incomes != null ? List.copyOf(incomes) : List.of();
    savings = savings != null ? List.copyOf(savings) : List.of();
    investments = investments != null ? List.copyOf(investments) : List.of();
    housing = housing != null ? List.copyOf(housing) : List.of();
    utilities = utilities != null ? List.copyOf(utilities) : List.of();
    transportation = transportation != null ? List.copyOf(transportation) : List.of();
    food = food != null ? List.copyOf(food) : List.of();
    personal = personal != null ? List.copyOf(personal) : List.of();
    health = health != null ? List.copyOf(health) : List.of();
    lifestyle = lifestyle != null ? List.copyOf(lifestyle) : List.of();
    debts = debts != null ? List.copyOf(debts) : List.of();
  }

  /**
   * Additional constructor to convert monetary amounts from cents to dollars
   */
  public BudgetResponse(YearMonth budgetDate,
                        List<LineItemResponse> incomes,
                        List<LineItemResponse> savings,
                        List<LineItemResponse> investments,
                        List<LineItemResponse> housing,
                        List<LineItemResponse> utilities,
                        List<LineItemResponse> transportation,
                        List<LineItemResponse> food,
                        List<LineItemResponse> personal,
                        List<LineItemResponse> health,
                        List<LineItemResponse> lifestyle,
                        List<LineItemResponse> debts,
                        Integer totalPlannedIncome,
                        Integer totalReceived,
                        Integer totalPlannedExpense,
                        Integer totalSpent,
                        Integer totalRemaining,
                        Integer totalLeftToBudget) {
    this(
            budgetDate,
            incomes,
            savings,
            investments,
            housing,
            utilities,
            transportation,
            food,
            personal,
            health,
            lifestyle,
            debts,
            CentsToDollarsConverter.convert(totalPlannedIncome),
            CentsToDollarsConverter.convert(totalReceived),
            CentsToDollarsConverter.convert(totalPlannedExpense),
            CentsToDollarsConverter.convert(totalSpent),
            CentsToDollarsConverter.convert(totalRemaining),
            CentsToDollarsConverter.convert(totalLeftToBudget)
    );
  }
}
