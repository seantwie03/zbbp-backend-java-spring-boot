package me.seantwiehaus.zbbp.dao.repository;

import me.seantwiehaus.zbbp.dao.entity.BaseEntity;
import me.seantwiehaus.zbbp.dao.entity.TransactionEntity;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TransactionRepositoryIT {
  @Autowired
  TestEntityManager entityManager;
  @Autowired
  TransactionRepository repository;

  @Nested
  class FindAllByDateBetweenOrderByDateAscAmountDescTypeDesc {
    private final LocalDate startDate = LocalDate.of(2021, 1, 12);
    private final LocalDate endDate = LocalDate.of(2021, 1, 18);

    @Test
    void returnEntitiesBetweenTwoDatesInclusive() {
      // Given two entities inside the inclusive date range
      TransactionEntity inRange1 = createEntity().date(startDate).build();
      TransactionEntity inRange2 = createEntity().date(endDate).build();
      // And two entities outside the date range
      TransactionEntity outOfRange1 = createEntity().date(startDate.minusDays(1)).build();
      TransactionEntity outOfRange2 = createEntity().date(endDate.plusDays(1)).build();
      // That are persisted out of order
      persistAndFlushList(List.of(outOfRange2, outOfRange1, inRange2, inRange1));

      // When the method under test is called
      List<TransactionEntity> returned = repository.findAllByDateBetweenOrderByDateDescAmountDesc(startDate, endDate);

      // Then the two entities should be returned
      assertEquals(2, returned.size());
      // And the two returned entities should be the ones inside the date range
      assertTrue(returned.contains(inRange1));
      assertTrue(returned.contains(inRange2));
      // And the two outside the date range should not be returned
      assertFalse(returned.contains(outOfRange1));
      assertFalse(returned.contains(outOfRange2));
    }

    @Test
    void returnEntitiesInCorrectOrder() {
      // Given three entities
      TransactionEntity highestAmountOnSameDateFirst = createEntity()
          .date(startDate.plusDays(1))
          .amount(2600)
          .build();
      TransactionEntity lowestAmountOnSameDateSecond = createEntity()
          .date(startDate.plusDays(1))
          .amount(2500)
          .build();
      TransactionEntity lowestDateThird = createEntity()
          .date(startDate)
          .amount(100)
          .build();
      // That are persisted out of order
      persistAndFlushList(List.of(lowestDateThird, lowestAmountOnSameDateSecond, highestAmountOnSameDateFirst));

      // When the method under test is called
      List<TransactionEntity> returned = repository.findAllByDateBetweenOrderByDateDescAmountDesc(startDate, endDate);

      // Then the entities should be returned in the correct order
      assertEquals(highestAmountOnSameDateFirst, returned.get(0));
      assertEquals(lowestAmountOnSameDateSecond, returned.get(1));
      assertEquals(lowestDateThird, returned.get(2));
    }
  }

  private void persistAndFlushList(List<? extends BaseEntity> entities) {
    entities.forEach(entityManager::persist);
    entityManager.flush();
    entityManager.clear(); // Clear the context so that entities are not fetched from the first-level cache
  }

  private TransactionEntity.TransactionEntityBuilder<?, ?> createEntity() {
    return TransactionEntity.builder()
        .id(null)
        .date(LocalDate.now())
        .merchant("Merchant")
        .amount(2500)
        .lastModifiedAt(Instant.now());
  }
}
