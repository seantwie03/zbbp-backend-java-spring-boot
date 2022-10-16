package me.seantwiehaus.zbbp.mapper;

import me.seantwiehaus.zbbp.domain.Budget;
import me.seantwiehaus.zbbp.dto.response.BudgetResponse;
import org.mapstruct.Mapper;

@Mapper(uses = LineItemMapper.class, componentModel = "spring")
public interface BudgetMapper {
  BudgetResponse mapDomainToResponse(Budget domain);
}
