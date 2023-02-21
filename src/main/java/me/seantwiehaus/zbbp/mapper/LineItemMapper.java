package me.seantwiehaus.zbbp.mapper;

import me.seantwiehaus.zbbp.dao.entity.LineItemEntity;
import me.seantwiehaus.zbbp.domain.LineItem;
import me.seantwiehaus.zbbp.dto.request.LineItemRequest;
import me.seantwiehaus.zbbp.dto.response.LineItemResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(uses = { DollarsToCentsConverter.class, TransactionMapper.class }, componentModel = "spring")
public interface LineItemMapper {
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "lastModifiedAt", ignore = true)
  @Mapping(target = "transactions", ignore = true)
  @Mapping(target = "plannedAmount", qualifiedBy = DollarsToCentsMapper.class)
  LineItem mapToDomain(LineItemRequest request);

  LineItemEntity mapToEntity(LineItem domain);

  LineItem mapToDomain(LineItemEntity entity);

  List<LineItem> mapToDomains(List<LineItemEntity> entities);

  @Mapping(target = "totalTransactions", expression = "java(domain.calculateTotalTransactions())")
  @Mapping(target = "percentageOfPlanned", expression = "java(domain.calculatePercentageOfPlanned())")
  @Mapping(target = "totalRemaining", expression = "java(domain.calculateTotalRemaining())")
  LineItemResponse mapToResponse(LineItem domain);
}
