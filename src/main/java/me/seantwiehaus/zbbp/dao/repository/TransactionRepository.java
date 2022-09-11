package me.seantwiehaus.zbbp.dao.repository;

import me.seantwiehaus.zbbp.dao.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Set;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
    Set<TransactionEntity> findAllByDateBetween(LocalDate startDate, LocalDate endDate);
}
