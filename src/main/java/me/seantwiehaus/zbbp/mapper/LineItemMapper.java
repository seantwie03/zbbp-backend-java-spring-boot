package me.seantwiehaus.zbbp.mapper;

import me.seantwiehaus.zbbp.dao.entity.LineItemEntity;
import me.seantwiehaus.zbbp.domain.LineItem;
import me.seantwiehaus.zbbp.dto.request.LineItemRequest;
import me.seantwiehaus.zbbp.dto.response.LineItemResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(uses = TransactionMapper.class, componentModel = "spring")
public interface LineItemMapper {
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "lastModifiedAt", ignore = true)
  @Mapping(target = "transactions", ignore = true)
  LineItem mapRequestToDomain(LineItemRequest request);

  LineItemEntity mapDomainToEntity(LineItem domain);

  LineItem mapEntityToDomain(LineItemEntity entity);

  List<LineItem> mapEntitiesToDomains(List<LineItemEntity> entities);

  @Mapping(target = "totalTransactions", expression = "java(domain.calculateTotalTransactions())")
  @Mapping(target = "percentageOfPlanned", expression = "java(domain.calculatePercentageOfPlanned())")
  @Mapping(target = "totalRemaining", expression = "java(domain.calculateTotalRemaining())")
  LineItemResponse mapDomainToResponse(LineItem domain);
}
