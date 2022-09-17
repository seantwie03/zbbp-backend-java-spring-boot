package me.seantwiehaus.zbbp.service;

import lombok.extern.slf4j.Slf4j;
import me.seantwiehaus.zbbp.dao.entity.TransactionEntity;
import me.seantwiehaus.zbbp.dao.repository.TransactionRepository;
import me.seantwiehaus.zbbp.domain.Transaction;
import me.seantwiehaus.zbbp.exception.ResourceConflictException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Stream;

@Slf4j
@Service
public class TransactionService {
    private final TransactionRepository repository;

    public TransactionService(TransactionRepository repository) {
        this.repository = repository;
    }

    /**
     * @param startDate Include transactions with dates greater-than-or-equal-to this day
     * @param endDate   Include transactions with dates less-than-or-equal-to this day
     * @return All transactions with dates between the start and end dates (inclusive)
     */
    public Stream<Transaction> getAllBetween(LocalDate startDate, LocalDate endDate) {
        return repository.findAllByDateBetweenOrderByDateAsc(startDate, endDate)
                .stream()
                .map(TransactionEntity::convertToTransaction);
    }

    public Optional<Transaction> findById(Long id) {
        return repository.findById(id)
                .map(TransactionEntity::convertToTransaction);
    }

    public Transaction create(Transaction transaction) {
        log.info("Creating new transaction -> " + transaction);
        return repository.save(new TransactionEntity(transaction))
                .convertToTransaction();
    }

    /**
     * Updates the fields in the specified transaction
     *
     * @param id          The ID of the transaction to update
     * @param transaction The values to update
     * @return The updated Transaction
     */
    public Optional<Transaction> update(Long id, Transaction transaction) {
        Optional<TransactionEntity> existingEntity = repository.findById(id);
        return existingEntity
                .map(entity -> {
                    if (entity.getLastModifiedAt().isAfter(transaction.getLastModifiedAt())) {
                        throw new ResourceConflictException(
                                "Transaction with ID: " + id + " has been modified since this client requested it.");
                    }
                    entity.setAmount(transaction.getAmount());
                    entity.setDate(transaction.getDate());
                    entity.setDescription(transaction.getDescription());
                    log.info("Updating transaction with ID=" + id + " -> " + entity);
                    return Optional.of(
                            repository.save(entity)
                                    .convertToTransaction());
                })
                .orElse(Optional.empty());
    }
}
