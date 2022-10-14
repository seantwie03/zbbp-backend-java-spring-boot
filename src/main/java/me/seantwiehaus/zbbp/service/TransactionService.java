package me.seantwiehaus.zbbp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.seantwiehaus.zbbp.dao.entity.TransactionEntity;
import me.seantwiehaus.zbbp.dao.repository.TransactionRepository;
import me.seantwiehaus.zbbp.domain.LineItem;
import me.seantwiehaus.zbbp.domain.Transaction;
import me.seantwiehaus.zbbp.exception.BadRequestException;
import me.seantwiehaus.zbbp.exception.InternalServerException;
import me.seantwiehaus.zbbp.exception.ResourceConflictException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class TransactionService {
  private final TransactionRepository repository;
  private final LineItemService lineItemService;

  /**
   * @param startDate Include transactions with dates greater-than-or-equal-to this day
   * @param endDate   Include transactions with dates less-than-or-equal-to this day
   * @return All transactions with dates between the start and end dates (inclusive)
   */
  public List<Transaction> getAllBetween(LocalDate startDate, LocalDate endDate) {
    return repository.findAllByDateBetweenOrderByDateDescAmountDescTypeDesc(startDate, endDate)
        .stream()
        .map(TransactionEntity::convertToTransaction)
        .toList();
  }

  public Optional<Transaction> findById(Long id) {
    return repository.findById(id)
        .map(TransactionEntity::convertToTransaction);
  }

  public Transaction create(Transaction transaction) {
    log.info("Creating new Transaction -> " + transaction);
    throwIfLineItemHasDifferentType(transaction);
    return repository.save(new TransactionEntity(transaction)).convertToTransaction();
  }

  private void throwIfLineItemHasDifferentType(Transaction transaction) {
    if (transaction.getLineItemId() == null) return;
    Optional<LineItem> lineItemOptional = lineItemService.findById(transaction.getLineItemId());
    LineItem lineItem = lineItemOptional.orElseThrow(
        () -> new BadRequestException("Unable to find Line Item with ID: " + transaction.getLineItemId()));
    if (lineItem.getType() != transaction.getType()) {
      throw new BadRequestException("Unable to add Transaction with type: " + transaction.getType().toString() +
          " to Line Item with type: " + lineItem.getType().toString());
    }
  }

  public Optional<Transaction> update(Long id, Instant ifUnmodifiedSince, Transaction transaction) {
    Optional<TransactionEntity> existingEntity = repository.findById(id);
    return existingEntity
        .map(entity -> {
          if (entity.getLastModifiedAt().isAfter(ifUnmodifiedSince)) {
            throw new ResourceConflictException(
                "Transaction with ID: " + id + " has been modified since this client requested it.");
          }
          throwIfLineItemHasDifferentType(transaction);
          entity.setAmount(transaction.getAmount());
          entity.setDate(transaction.getDate());
          entity.setDescription(transaction.getDescription());
          entity.setLineItemId(transaction.getLineItemId());
          log.info("Updating Transaction with ID=" + id + " -> " + entity);
          return Optional.of(repository.save(entity).convertToTransaction());
        })
        .orElse(Optional.empty());
  }

  public Optional<Long> delete(Long id) {
    if (id == null) throw new InternalServerException("Unable to delete Transaction. ID is null");
    return repository.findById(id)
        .map(entity -> {
          log.info("Deleting Transaction with ID=" + id + " -> " + entity);
          repository.delete(entity);
          return id;
        });
  }
}
