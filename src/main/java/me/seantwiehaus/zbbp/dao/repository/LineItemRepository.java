package me.seantwiehaus.zbbp.dao.repository;

import me.seantwiehaus.zbbp.dao.entity.LineItemEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface LineItemRepository extends JpaRepository<LineItemEntity, Long> {
  @EntityGraph("lineItem.transactions")
  Optional<LineItemEntity> findLineItemEntityById(Long id);

  @EntityGraph("lineItem.transactions")
  List<LineItemEntity> findAllByBudgetDateBetween(LocalDate startDate, LocalDate endDate);
}
