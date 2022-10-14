package me.seantwiehaus.zbbp.dao.repository;

import me.seantwiehaus.zbbp.dao.entity.TransactionEntity;
import me.seantwiehaus.zbbp.domain.ItemType;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

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
    @Test
    void findTransactionsBetweenTwoDatesInclusive() {
      // Given four TransactionEntities with different dates in January 2022
      TransactionEntity shouldNotBeReturned1 = createEntity(LocalDate.of(2022, 1, 1));
      entityManager.persist(shouldNotBeReturned1);
      TransactionEntity shouldBeReturned1 = createEntity(LocalDate.of(2022, 1, 12));
      entityManager.persist(shouldBeReturned1);
      TransactionEntity shouldBeReturned2 = createEntity(LocalDate.of(2022, 1, 18));
      entityManager.persist(shouldBeReturned2);
      TransactionEntity shouldNotBeReturned2 = createEntity(LocalDate.of(2022, 1, 20));
      entityManager.persist(shouldNotBeReturned2);
      entityManager.flush();
      entityManager.clear(); // Clear the context so that entities are not fetched from the first-level cache

      // When the method under test is called with the 12th - 18th of January
      List<TransactionEntity> between = repository.findAllByDateBetweenOrderByDateDescAmountDescTypeDesc(
          LocalDate.of(2022, 1, 12),
          LocalDate.of(2022, 1, 18));

      // Then the two TransactionEntities between the 12th and 18th should be returned
      assertTrue(between.contains(shouldBeReturned1));
      assertTrue(between.contains(shouldBeReturned2));
      // And the two outside those boundaries should not
      assertFalse(between.contains(shouldNotBeReturned1));
      assertFalse(between.contains(shouldNotBeReturned2));
    }

    @Test
    void findTransactionsBetweenTwoDatesWithCorrectOrder() {
      // Given four TransactionEntities with values
      TransactionEntity sameDateAndAmountIncomeFirst = createEntity(LocalDate.of(2022, 1, 2), 2600, ItemType.INCOME);
      entityManager.persist(sameDateAndAmountIncomeFirst);
      TransactionEntity sameDateAndAmountExpenseSecond = createEntity(LocalDate.of(2022, 1, 2), 2600, ItemType.EXPENSE);
      entityManager.persist(sameDateAndAmountExpenseSecond);
      TransactionEntity lowestAmountOnSameDateThird = createEntity(LocalDate.of(2022, 1, 2), 2500, ItemType.INCOME);
      entityManager.persist(lowestAmountOnSameDateThird);
      TransactionEntity lowestDateFourth = createEntity(LocalDate.of(2022, 1, 1), 100, ItemType.INCOME);
      entityManager.persist(lowestDateFourth);
      entityManager.flush();
      entityManager.clear(); // Clear the context so that entities are not fetched from the first-level cache

      // When the method under test is called
      List<TransactionEntity> returned = repository.findAllByDateBetweenOrderByDateDescAmountDescTypeDesc(
          LocalDate.of(2022, 1, 1),
          LocalDate.of(2022, 1, 3));

      // Then the TransactionEntities should be returned in the correct order
      assertEquals(sameDateAndAmountIncomeFirst, returned.get(0));
      assertEquals(sameDateAndAmountExpenseSecond, returned.get(1));
      assertEquals(lowestAmountOnSameDateThird, returned.get(2));
      assertEquals(lowestDateFourth, returned.get(3));
    }
  }

  private TransactionEntity createEntity(LocalDate date) {
    TransactionEntity transactionEntity = new TransactionEntity();
    transactionEntity.setDate(date);
    transactionEntity.setMerchant("Merchant");
    transactionEntity.setAmount(2500);
    return transactionEntity;
  }

  private TransactionEntity createEntity(LocalDate date, int amount, ItemType type) {
    TransactionEntity transactionEntity = new TransactionEntity();
    transactionEntity.setType(type);
    transactionEntity.setDate(date);
    transactionEntity.setMerchant("Merchant");
    transactionEntity.setAmount(amount);
    return transactionEntity;
  }
}
