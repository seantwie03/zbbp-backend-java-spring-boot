package me.seantwiehaus.zbbp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.seantwiehaus.zbbp.dao.entity.TransactionEntity;
import me.seantwiehaus.zbbp.dao.repository.TransactionRepository;
import me.seantwiehaus.zbbp.domain.Transaction;
import me.seantwiehaus.zbbp.exception.ResourceNotFoundException;
import me.seantwiehaus.zbbp.mapper.TransactionMapper;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import static me.seantwiehaus.zbbp.validation.IfUnmodifiedSinceValidation.throwWhenEntityLastModifiedAtIsAfterIfUnmodifiedSince;

@RequiredArgsConstructor
@Slf4j
@Service
public class TransactionService {
  private final TransactionRepository repository;
  private final TransactionMapper mapper;

  /**
   * @param startDate The first Date to include in the list of results.
   * @param endDate   The last Date to include in the list of results.
   * @return All Transactions between the start and end Dates (inclusive).
   */
  public List<Transaction> getAllBetween(LocalDate startDate, LocalDate endDate) {
    return repository.findAllByDateBetweenOrderByDateDescAmountDesc(startDate, endDate)
        .stream()
        .map(mapper::mapEntityToDomain)
        .toList();
  }

  public Transaction findById(Long id) {
    TransactionEntity entity = repository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Transaction", id));
    return mapper.mapEntityToDomain(entity);
  }

  public Transaction create(Transaction transaction) {
    TransactionEntity entity = mapper.mapDomainToEntity(transaction);
    log.info("Creating new TransactionEntity -> %s ".formatted(entity));
    TransactionEntity saved = repository.save(entity);
    return mapper.mapEntityToDomain(saved);
  }

  public Transaction update(Long id, Instant ifUnmodifiedSince, Transaction transaction) {
    TransactionEntity entity = repository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Transaction", id));
    throwWhenEntityLastModifiedAtIsAfterIfUnmodifiedSince(entity, ifUnmodifiedSince);
    entity.setDate(transaction.date());
    entity.setMerchant(transaction.merchant());
    entity.setAmount(transaction.amount());
    entity.setLineItemId(transaction.lineItemId());
    entity.setDescription(transaction.description());
    log.info("Updating TransactionEntity with ID=%d -> %s".formatted(id, entity));
    TransactionEntity saved = repository.save(entity);
    return mapper.mapEntityToDomain(saved);
  }

  public void delete(Long id) {
    TransactionEntity entity = repository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Transaction", id));
    log.info("Deleting TransactionEntity with ID=%d -> %s".formatted(id, entity));
    repository.delete(entity);
  }
}
