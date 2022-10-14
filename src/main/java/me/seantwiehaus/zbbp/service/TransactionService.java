package me.seantwiehaus.zbbp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.seantwiehaus.zbbp.dao.entity.TransactionEntity;
import me.seantwiehaus.zbbp.dao.repository.TransactionRepository;
import me.seantwiehaus.zbbp.domain.Transaction;
import me.seantwiehaus.zbbp.exception.NotFoundException;
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
   * @param startDate The first Date to include in the list of results.
   * @param endDate   The last Date to include in the list of results.
   * @return All Transactions between the start and end Dates (inclusive).
   */
  public List<Transaction> getAllBetween(LocalDate startDate, LocalDate endDate) {
    return repository.findAllByDateBetweenOrderByDateDescAmountDesc(startDate, endDate)
        .stream()
        .map(TransactionEntity::convertToTransaction)
        .toList();
  }

  public Transaction findById(Long id) {
    return repository.findById(id)
        .map(TransactionEntity::convertToTransaction)
        .orElseThrow(() -> new NotFoundException("Transaction", id));
  }

  public Transaction create(Transaction transaction) {
    log.info("Creating new Transaction -> " + transaction);
    return repository.save(new TransactionEntity(transaction)).convertToTransaction();
  }

  public Transaction update(Long id, Instant ifUnmodifiedSince, Transaction transaction) {
    Optional<TransactionEntity> existingEntity = repository.findById(id);
    return existingEntity
        .map(entity -> {
          if (entity.getLastModifiedAt().isAfter(ifUnmodifiedSince)) {
            throw new ResourceConflictException(
                "Transaction with ID: " + id + " has been modified since this client requested it.");
          }
          entity.setAmount(transaction.getAmount());
          entity.setDate(transaction.getDate());
          entity.setDescription(transaction.getDescription());
          entity.setLineItemId(transaction.getLineItemId());
          log.info("Updating Transaction with ID=" + id + " -> " + entity);
          return repository.save(entity).convertToTransaction();
        })
        .orElseThrow(() -> new NotFoundException("Transaction", id));
  }

  public void delete(Long id) {
    TransactionEntity entity = repository.findById(id).orElseThrow(() -> new NotFoundException("Transaction", id));
    log.info("Deleting Transaction with ID=" + id + " -> " + entity);
    repository.delete(entity);
  }
}
