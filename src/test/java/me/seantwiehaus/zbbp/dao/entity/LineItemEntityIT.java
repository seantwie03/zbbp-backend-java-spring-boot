package me.seantwiehaus.zbbp.dao.entity;

import me.seantwiehaus.zbbp.domain.Category;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.jdbc.Sql;

import javax.persistence.PersistenceException;
import java.time.LocalDate;
import java.time.YearMonth;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class LineItemEntityIT {
  @Autowired
  private TestEntityManager entityManager;

  @Nested
  class WhenRetrievingTheEntity {
    @Test
    // SQL script is used because it is not possible to save mixed-case via TestEntityManager.
    @Sql("insertMixedCaseCategoryIntoLineItems.sql")
    void convertDbDataToCategoryEnumWhenMixedCaseDbData() {
      // Given a database record with mixed-case Type and Category (ID = 1 from @SQL)

      // When that record is retrieved
      LineItemEntity lineItem = entityManager.find(LineItemEntity.class, 1L);

      // Then the Enum values should be set correctly
      assertEquals(Category.FOOD, lineItem.getCategory());
    }

    @Test
    void convertDbDateToEntityYearMonth() {
      // Given a LineItemEntity with specific YearMonth
      LineItemEntity expected = createLineItemEntity("Convert DB Date To", YearMonth.now(), Category.FOOD);
      entityManager.persistAndFlush(expected);
      entityManager.clear();

      // When that LineItemEntity is retrieved
      LineItemEntity retrieved = entityManager.find(LineItemEntity.class, expected.getId());

      // Then the YearMonth should be the same (This means it converted to java.sql.Date and back correctly)
      assertEquals(expected.getBudgetDate(), retrieved.getBudgetDate());
    }

    @Test
    void sortTransactionsByDateAndAmount() {
      // Given a LineItemEntity
      LineItemEntity mockLineItem = new LineItemEntity();
      mockLineItem.setBudgetDate(YearMonth.now());
      mockLineItem.setName("Sort Transactions By Date And");
      mockLineItem.setPlannedAmount(1000);
      mockLineItem.setCategory(Category.FOOD);
      entityManager.persistAndFlush(mockLineItem);
      // And three Transactions associated to that LineItemEntity
      // Note: Saving transactionEntities out of order to fully test sorting
      TransactionEntity shouldBeThird = createTransactionEntity(LocalDate.of(2022, 2, 1), 10, mockLineItem.getId());
      entityManager.persistAndFlush(shouldBeThird);
      TransactionEntity shouldBeSecond = createTransactionEntity(LocalDate.of(2022, 2, 1), 20, mockLineItem.getId());
      entityManager.persistAndFlush(shouldBeSecond);
      TransactionEntity shouldBeFirst = createTransactionEntity(LocalDate.of(2022, 1, 1), 1, mockLineItem.getId());
      entityManager.persistAndFlush(shouldBeFirst);
      entityManager.clear(); // Clear context so that entities are not fetched from first-level cache

      // When that record is retrieved from the database
      LineItemEntity lineItem = entityManager.find(LineItemEntity.class, mockLineItem.getId());

      // Then there should be three TransactionEntities
      assertEquals(3, lineItem.getTransactionEntities().size());
      // And the TransactionEntities should be sorted by Date then by Amount
      assertEquals(shouldBeFirst.getId(), lineItem.getTransactionEntities().get(0).getId());
      assertEquals(shouldBeSecond.getId(), lineItem.getTransactionEntities().get(1).getId());
      assertEquals(shouldBeThird.getId(), lineItem.getTransactionEntities().get(2).getId());
    }
  }

  @Nested
  class WhenPersistingTheEntity {
    @Test
    void enforceCaseInsensitiveUniqueConstraint() {
      // Given two entities with the same name, budgetDate, and category
      LineItemEntity lineItem1 = createLineItemEntity("eNfOrCe CaSe InSeNsItIvE uNiQu", YearMonth.now(), Category.FOOD);
      LineItemEntity lineItem2 = createLineItemEntity("EnFoRcE cAsE iNsEnSiTiVe UnIqU", YearMonth.now(), Category.FOOD);

      // When the first entity is persisted, no exceptions should be thrown
      assertDoesNotThrow(() -> entityManager.persistAndFlush(lineItem1));

      // Then, when the second entity is saved, an exception should be thrown
      PersistenceException exception =
          assertThrows(PersistenceException.class, () -> entityManager.persistAndFlush(lineItem2));
      // And the exception should contain the following
      assertTrue(exception.getCause().getCause().getMessage()
          .contains("duplicate key value violates unique constraint \"unique_insensitive_name_category_date_idx\""));
    }

    @Test
    void twoLineItemsCanHaveSameNameWhenCategoryIsDifferent() {
      // Given two entities with the same name, budgetDate but different categories
      LineItemEntity lineItem1 = createLineItemEntity("Have Same Name Not Category", YearMonth.now(), Category.HEALTH);
      LineItemEntity lineItem2 = createLineItemEntity("Have Same Name Not Category", YearMonth.now(), Category.HOUSING);

      // When the first entity is saved, no exceptions should be thrown
      assertDoesNotThrow(() -> entityManager.persistAndFlush(lineItem1));

      // Then, when the second entity is saved, no exceptions should be thrown
      assertDoesNotThrow(() -> entityManager.persistAndFlush(lineItem2));
    }
  }

  /**
   * These test are designed to run in parallel. This means if you try to persist a LineItemEntity that has the
   * same name, budgetDate, and Category as another test, you will get Unique Constraint Violation errors.
   * To avoid this, name the entity after the test class and method because that combination is guaranteed to be unique.
   */
  private LineItemEntity createLineItemEntity(String name, YearMonth budgetDate, Category category) {
    LineItemEntity lineItemEntity = new LineItemEntity();
    lineItemEntity.setBudgetDate(budgetDate);
    lineItemEntity.setName(name);
    lineItemEntity.setPlannedAmount(120000);
    lineItemEntity.setCategory(category);
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
