package me.seantwiehaus.zbbp.service;

import me.seantwiehaus.zbbp.dao.entity.TransactionEntity;
import me.seantwiehaus.zbbp.dao.repository.TransactionRepository;
import me.seantwiehaus.zbbp.domain.Transaction;
import me.seantwiehaus.zbbp.exception.ResourceConflictException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
    public List<Transaction> getAllBetween(LocalDate startDate, LocalDate endDate) {
        return repository.findAllByDateBetweenOrderByDateAsc(startDate, endDate)
                .stream()
                .map(TransactionEntity::convertToTransaction)
                .toList();
    }

    public Optional<Transaction> findById(Long id) {
        return repository.findById(id)
                .map(TransactionEntity::convertToTransaction);
    }

    public Transaction create(Transaction transaction) {
        return repository.save(new TransactionEntity(transaction))
                .convertToTransaction();
    }

    public Optional<Transaction> update(Long id, Transaction transaction) {
        Optional<TransactionEntity> existingEntity = repository.findById(id);
        return existingEntity
                .map(entity -> {
                    if (entity.getLastModifiedAt().isAfter(transaction.getLastModifiedAt())) {
                        throw new ResourceConflictException(
                                "Transaction with Id: " + id + " has been modified since this client requested it.");
                    }
                    entity.setAmount(transaction.getAmount());
                    entity.setDate(transaction.getDate());
                    entity.setDescription(transaction.getDescription());
                    return Optional.of(
                            repository.save(entity)
                                    .convertToTransaction());
                })
                .orElse(Optional.empty());
    }
}
