package me.seantwiehaus.zbbp.mapper;

import lombok.RequiredArgsConstructor;
import me.seantwiehaus.zbbp.domain.Budget;
import me.seantwiehaus.zbbp.dto.response.BudgetResponse;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BudgetMapper {
  private final LineItemMapper lineItemMapper;

  public BudgetResponse mapToResponse(Budget domain) {
    if (domain == null) {
      return null;
    }

    return new BudgetResponse(
            domain.getYearMonth(),
            lineItemMapper.mapToResponses(domain.getIncomes()),
            lineItemMapper.mapToResponses(domain.getSavings()),
            lineItemMapper.mapToResponses(domain.getInvestments()),
            lineItemMapper.mapToResponses(domain.getHousing()),
            lineItemMapper.mapToResponses(domain.getUtilities()),
            lineItemMapper.mapToResponses(domain.getTransportation()),
            lineItemMapper.mapToResponses(domain.getFood()),
            lineItemMapper.mapToResponses(domain.getPersonal()),
            lineItemMapper.mapToResponses(domain.getHealth()),
            lineItemMapper.mapToResponses(domain.getLifestyle()),
            lineItemMapper.mapToResponses(domain.getDebts()),
            domain.getTotalPlannedIncome(),
            domain.getTotalReceived(),
            domain.getTotalPlannedExpense(),
            domain.getTotalSpent(),
            domain.getTotalRemaining(),
            domain.getTotalLeftToBudget());
  }
}
