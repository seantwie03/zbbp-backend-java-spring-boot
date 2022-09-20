package me.seantwiehaus.zbbp.dao.repository;

import me.seantwiehaus.zbbp.dao.entity.CategoryEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
  @EntityGraph("category.group.transactions")
  Optional<CategoryEntity> findCategoryEntityById(Long id);

  @EntityGraph("category.group.transactions")
  List<CategoryEntity> findAllByBudgetDateBetween(LocalDate startDate, LocalDate endDate);
}
