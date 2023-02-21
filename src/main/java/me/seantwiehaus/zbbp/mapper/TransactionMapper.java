package me.seantwiehaus.zbbp.mapper;

import me.seantwiehaus.zbbp.dao.entity.TransactionEntity;
import me.seantwiehaus.zbbp.domain.Transaction;
import me.seantwiehaus.zbbp.dto.request.TransactionRequest;
import me.seantwiehaus.zbbp.dto.response.TransactionResponse;
import me.seantwiehaus.zbbp.exception.BadRequestException;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "lastModifiedAt", ignore = true)
  @Mapping(target = "amount", qualifiedByName = "dollarsToCents")
  Transaction mapToDomain(TransactionRequest request);

  @Named("dollarsToCents")
  default int convertDollarsToCents(double dollars) {
    if (dollars > Integer.MAX_VALUE / 100.0) {
      throw new BadRequestException("Dollars cannot be greater than " + Integer.MAX_VALUE / 100);
    }
    return Math.toIntExact(Math.round(dollars * 100));
  }

  TransactionEntity mapToEntity(Transaction domain);

  Transaction mapToDomain(TransactionEntity entity);

  TransactionResponse mapToResponse(Transaction domain);
}
