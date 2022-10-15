package me.seantwiehaus.zbbp.mapper;

import me.seantwiehaus.zbbp.dao.entity.LineItemEntity;
import me.seantwiehaus.zbbp.domain.LineItem;
import me.seantwiehaus.zbbp.dto.request.LineItemRequest;
import me.seantwiehaus.zbbp.dto.response.LineItemResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = TransactionMapper.class)
public interface LineItemMapper {
  LineItemMapper INSTANCE = Mappers.getMapper(LineItemMapper.class);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "lastModifiedAt", ignore = true)
  @Mapping(target = "transactions", ignore = true)
  LineItem requestToDomain(LineItemRequest request);

  LineItemEntity domainToEntity(LineItem domain);

  LineItem entityToDomain(LineItemEntity entity);

  List<LineItem> entitiesToDomains(List<LineItemEntity> entities);

  @Mapping(target = "totalTransactions", expression = "java(domain.calculateTotalTransactions())")
  @Mapping(target = "percentageOfPlanned", expression = "java(domain.calculatePercentageOfPlanned())")
  @Mapping(target = "totalRemaining", expression = "java(domain.calculateTotalRemaining())")
  LineItemResponse domainToResponse(LineItem domain);
}
