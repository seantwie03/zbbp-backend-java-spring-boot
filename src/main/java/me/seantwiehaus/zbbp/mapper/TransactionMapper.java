package me.seantwiehaus.zbbp.mapper;

import me.seantwiehaus.zbbp.dao.entity.TransactionEntity;
import me.seantwiehaus.zbbp.domain.Transaction;
import me.seantwiehaus.zbbp.dto.request.TransactionRequest;
import me.seantwiehaus.zbbp.dto.response.TransactionResponse;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class TransactionMapper {
  public Transaction mapToDomain(TransactionRequest request) {
    if (request == null) {
      return null;
    }

    return Transaction.builder(request.date(), request.merchant(), request.amount())
            .lineItemId(request.lineItemId())
            .description(request.description())
            .build();
  }

  public TransactionEntity mapToEntity(Transaction domain) {
    if (domain == null) {
      return null;
    }

    return TransactionEntity.builder()
            .id(domain.id())
            .date(domain.date())
            .merchant(domain.merchant())
            .amount(domain.amount())
            .lineItemId(domain.lineItemId())
            .description(domain.description())
            .lastModifiedAt(domain.lastModifiedAt())
            .build();
  }

  public List<TransactionEntity> mapToEntities(List<Transaction> domains) {
    if (domains == null) {
      return Collections.emptyList();
    }

    return domains.stream().map(this::mapToEntity).toList();
  }

  public Transaction mapToDomain(TransactionEntity entity) {
    if (entity == null) {
      return null;
    }

    return Transaction.builder(entity.getDate(), entity.getMerchant(), entity.getAmount())
            .id(entity.getId())
            .lineItemId(entity.getLineItemId())
            .description(entity.getDescription())
            .lastModifiedAt(entity.getLastModifiedAt())
            .build();
  }

  public List<Transaction> mapToDomains(List<TransactionEntity> entities) {
    if (entities == null) {
      return Collections.emptyList();
    }

    return entities.stream().map(this::mapToDomain).toList();
  }

  public TransactionResponse mapToResponse(Transaction domain) {
    if (domain == null) {
      return null;
    }

    return new TransactionResponse(
            domain.id(),
            domain.date(),
            domain.merchant(),
            domain.amount(),
            domain.lineItemId(),
            domain.description(),
            domain.lastModifiedAt());
  }

  public List<TransactionResponse> mapToResponses(List<Transaction> domains) {
    if (domains == null) {
      return Collections.emptyList();
    }

    return domains.stream().map(this::mapToResponse).toList();
  }
}
