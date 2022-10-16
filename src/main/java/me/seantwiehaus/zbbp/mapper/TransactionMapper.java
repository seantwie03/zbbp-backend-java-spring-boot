package me.seantwiehaus.zbbp.mapper;

import me.seantwiehaus.zbbp.dao.entity.TransactionEntity;
import me.seantwiehaus.zbbp.domain.Transaction;
import me.seantwiehaus.zbbp.dto.request.TransactionRequest;
import me.seantwiehaus.zbbp.dto.response.TransactionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "lastModifiedAt", ignore = true)
  Transaction mapRequestToDomain(TransactionRequest request);

  TransactionEntity mapDomainToEntity(Transaction domain);

  Transaction mapEntityToDomain(TransactionEntity entity);

  TransactionResponse mapDomainToResponse(Transaction domain);
}
