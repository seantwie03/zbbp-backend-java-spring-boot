package me.seantwiehaus.zbbp.dao.repository;

import me.seantwiehaus.zbbp.dao.entity.CategoryGroupEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CategoryGroupRepository extends JpaRepository<CategoryGroupEntity, Long> {
    @EntityGraph("group.categories.transactions")
    List<CategoryGroupEntity> findAllByBudgetDateBetween(LocalDate startDate, LocalDate endDate);
}
