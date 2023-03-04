package me.seantwiehaus.zbbp.mapper;

import lombok.RequiredArgsConstructor;
import me.seantwiehaus.zbbp.dao.entity.LineItemEntity;
import me.seantwiehaus.zbbp.domain.LineItem;
import me.seantwiehaus.zbbp.dto.request.LineItemRequest;
import me.seantwiehaus.zbbp.dto.response.LineItemResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class LineItemMapper {
  private final TransactionMapper transactionMapper;

  public LineItem mapToDomain(LineItemRequest request) {
    if (request == null) {
      return null;
    }

    return LineItem.builder(request.budgetDate(), request.name(), request.plannedAmount(), request.category())
            .category(request.category())
            .description(request.description())
            .build();
  }

  public LineItemEntity mapToEntity(LineItem domain) {
    if (domain == null) {
      return null;
    }

    return LineItemEntity.builder()
            .id(domain.id())
            .budgetDate(domain.budgetDate())
            .name(domain.name())
            .plannedAmount(domain.plannedAmount())
            .category(domain.category())
            .description(domain.description())
            .lastModifiedAt(domain.lastModifiedAt())
            .transactions(transactionMapper.mapToEntities(domain.transactions()))
            .build();
  }

  public LineItem mapToDomain(LineItemEntity entity) {
    if (entity == null) {
      return null;
    }

    return LineItem.builder(entity.getBudgetDate(), entity.getName(), entity.getPlannedAmount(), entity.getCategory())
            .id(entity.getId())
            .description(entity.getDescription())
            .lastModifiedAt(entity.getLastModifiedAt())
            .transactions(transactionMapper.mapToDomains(entity.getTransactions()))
            .build();
  }

  public List<LineItem> mapToDomains(List<LineItemEntity> entities) {
    if (entities == null) {
      return List.of();
    }

    return entities.stream().map(this::mapToDomain).toList();
  }

  public LineItemResponse mapToResponse(LineItem domain) {
    if (domain == null) {
      return null;
    }

    return new LineItemResponse(
            domain.id(),
            domain.budgetDate(),
            domain.name(),
            domain.plannedAmount(),
            domain.category(),
            domain.description(),
            domain.lastModifiedAt(),
            domain.calculateTotalTransactions(),
            domain.calculatePercentageOfPlanned(),
            domain.calculateTotalRemaining(),
            transactionMapper.mapToResponses(domain.transactions()));
  }

  public List<LineItemResponse> mapToResponses(List<LineItem> domains) {
    if (domains == null) {
      return List.of();
    }

    return domains.stream().map(this::mapToResponse).toList();
  }
}
