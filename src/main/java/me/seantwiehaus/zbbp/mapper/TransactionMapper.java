package me.seantwiehaus.zbbp.mapper;

import me.seantwiehaus.zbbp.dao.entity.TransactionEntity;
import me.seantwiehaus.zbbp.domain.Transaction;
import me.seantwiehaus.zbbp.dto.request.TransactionRequest;
import me.seantwiehaus.zbbp.dto.response.TransactionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = DollarsToCentsConverter.class, componentModel = "spring")
public interface TransactionMapper {
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "lastModifiedAt", ignore = true)
  @Mapping(target = "amount", qualifiedBy = DollarsToCentsMapper.class)
  Transaction mapToDomain(TransactionRequest request);

  TransactionEntity mapToEntity(Transaction domain);

  Transaction mapToDomain(TransactionEntity entity);

  TransactionResponse mapToResponse(Transaction domain);
}
