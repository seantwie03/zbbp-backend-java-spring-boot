package me.seantwiehaus.zbbp.mapper;

import me.seantwiehaus.zbbp.domain.Budget;
import me.seantwiehaus.zbbp.dto.response.BudgetResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = LineItemMapper.class)
public interface BudgetMapper {
  BudgetMapper INSTANCE = Mappers.getMapper(BudgetMapper.class);

  BudgetResponse domainToResponse(Budget domain);
}
