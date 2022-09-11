package me.seantwiehaus.zbbp.service;

import me.seantwiehaus.zbbp.dao.entity.TransactionEntity;
import me.seantwiehaus.zbbp.dao.repository.TransactionRepository;
import me.seantwiehaus.zbbp.domain.Transaction;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

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
    public Set<Transaction> getAllTransactionsBetween(LocalDate startDate, LocalDate endDate) {
        return repository.findAllByDateBetween(startDate, endDate).stream()
                .map(TransactionEntity::convertToTransaction)
                .collect(Collectors.toSet());
    }
}
