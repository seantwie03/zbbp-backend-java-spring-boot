package me.seantwiehaus.zbbp.dto.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.seantwiehaus.zbbp.dto.serialize.CentsToDollarsSerializer;

import java.time.YearMonth;
import java.util.List;

@Getter
@AllArgsConstructor
public class BudgetResponse {
  private final YearMonth budgetDate;
  private final List<LineItemResponse> incomes;
  private final List<LineItemResponse> savings;
  private final List<LineItemResponse> investments;
  private final List<LineItemResponse> housing;
  private final List<LineItemResponse> transportation;
  private final List<LineItemResponse> food;
  private final List<LineItemResponse> personal;
  private final List<LineItemResponse> health;
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
}
