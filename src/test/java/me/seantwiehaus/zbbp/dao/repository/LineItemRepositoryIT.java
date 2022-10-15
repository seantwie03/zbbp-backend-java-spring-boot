package me.seantwiehaus.zbbp.dao.repository;

import me.seantwiehaus.zbbp.dao.entity.LineItemEntity;
import me.seantwiehaus.zbbp.dao.entity.TransactionEntity;
import me.seantwiehaus.zbbp.domain.Category;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class LineItemRepositoryIT {
  @Autowired
  TestEntityManager entityManager;
  @Autowired
  LineItemRepository repository;

  @Nested
  class FindTopByOrderByBudgetDateDesc {
    @Test
    void getMostRecentLineItem() {
      // Given two LineItems with different dates
      LineItemEntity shouldBeTop = createLineItemEntity(YearMonth.of(2022, 2));
      entityManager.persistAndFlush(shouldBeTop);
      LineItemEntity shouldNot = createLineItemEntity(YearMonth.of(2022, 1));
      entityManager.persistAndFlush(shouldNot);
      entityManager.clear(); // Clear the context so that entities are not fetched from the first-level cache

      // When the method under test is called
      Optional<LineItemEntity> mostRecent = repository.findTopByOrderByBudgetDateDesc();

      // Then the most recent LineItemEntity should be returned
      assertTrue(mostRecent.isPresent());
      assertEquals(shouldBeTop, mostRecent.get());
      assertEquals(YearMonth.of(2022, 2), mostRecent.get().getBudgetDate());
    }
  }

  @Nested
  class FindAllByBudgetDateBetween {
    @Test
    void getLineItemsBetweenTwoDates() {
      // Given three LineItems with different dates
      LineItemEntity shouldBeIncluded = createLineItemEntity(YearMonth.of(2022, 2));
      entityManager.persistAndFlush(shouldBeIncluded);
      LineItemEntity shouldAlsoBeIncluded = createLineItemEntity(YearMonth.of(2022, 1));
      entityManager.persistAndFlush(shouldAlsoBeIncluded);
      LineItemEntity shouldNotBeIncluded = createLineItemEntity(YearMonth.of(2022, 3));
      entityManager.persistAndFlush(shouldNotBeIncluded);
      entityManager.clear(); // Clear the context so that entities are not fetched from the first-level cache

      // When the method under test is called
      List<LineItemEntity> allBetween =
          repository.findAllByBudgetDateBetween(YearMonth.of(2022, 1), YearMonth.of(2022, 2));

      // Then only one LineItem should be returned
      assertEquals(2, allBetween.size());
      // And the LineItems for 2022,1 and 2022,2 should be included in the list
      assertTrue(allBetween.stream().allMatch(item -> item.equals(shouldBeIncluded) || item.equals(shouldAlsoBeIncluded)));
      // And the LineItems for 2022,3 should NOT
      assertTrue(allBetween.stream().noneMatch(item -> item.equals(shouldNotBeIncluded)));
    }
  }

  @Nested
  class Delete {
    @Test
    void deleteLineItemWithNoTransactions() {
      // Given a LineItem with no Transactions
      LineItemEntity toDelete = createLineItemEntity(YearMonth.now());
      entityManager.persistAndFlush(toDelete);
      entityManager.clear();

      // When the method under test is called
      repository.delete(toDelete);

      // Then the item should be deleted
      Optional<LineItemEntity> shouldBeDeleted = repository.findLineItemEntityById(toDelete.getId());
      assertTrue(shouldBeDeleted.isEmpty());
    }

    @Test
    void transactionsAreNotDeletedWhenAssociatedLineItemIsDeleted() {
      // Given a LineItem with two Transactions
      LineItemEntity toDelete = createLineItemEntity(YearMonth.now());
      entityManager.persistAndFlush(toDelete); // Persist and flush to get ID
      TransactionEntity transaction1 = createTransactionEntity(LocalDate.now(), 5000, toDelete.getId());
      entityManager.persist(transaction1);
      TransactionEntity transaction2 = createTransactionEntity(LocalDate.now(), 6000, toDelete.getId());
      entityManager.persist(transaction2);
      toDelete.setTransactions(List.of(transaction1, transaction2));
      entityManager.persist(toDelete);
      entityManager.flush();
      entityManager.clear();

      // When the method under test is called
      repository.delete(toDelete);

      // Then the item should be deleted
      Optional<LineItemEntity> shouldBeDeleted = repository.findLineItemEntityById(toDelete.getId());
      assertTrue(shouldBeDeleted.isEmpty());
      // And the Transactions should still exist in the DB
      TransactionEntity shouldExist1 = entityManager.find(TransactionEntity.class, transaction1.getId());
      assertNotNull(shouldExist1);
      TransactionEntity shouldExist2 = entityManager.find(TransactionEntity.class, transaction2.getId());
      assertNotNull(shouldExist2);
    }
  }

  private LineItemEntity createLineItemEntity(YearMonth budgetDate) {
    LineItemEntity lineItemEntity = new LineItemEntity();
    lineItemEntity.setBudgetDate(budgetDate);
    lineItemEntity.setName("Name");
    lineItemEntity.setPlannedAmount(120000);
    lineItemEntity.setCategory(Category.FOOD);
    return lineItemEntity;
  }

  private TransactionEntity createTransactionEntity(LocalDate date, int amount, Long lineItemId) {
    TransactionEntity transactionEntity = new TransactionEntity();
    transactionEntity.setDate(date);
    transactionEntity.setMerchant("Merchant");
    transactionEntity.setAmount(amount);
    transactionEntity.setLineItemId(lineItemId);
    return transactionEntity;
  }
}
