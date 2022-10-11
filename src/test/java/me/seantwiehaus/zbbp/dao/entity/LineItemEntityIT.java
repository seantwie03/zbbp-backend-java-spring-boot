package me.seantwiehaus.zbbp.dao.entity;

import me.seantwiehaus.zbbp.domain.Category;
import me.seantwiehaus.zbbp.domain.ItemType;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.jdbc.Sql;

import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;
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
    @Sql("insertMixedCaseTypeAndCategoryIntoLineItems.sql")
    void shouldRetrieveEnumsFromDatabaseWhenDatabaseContainsMixedCaseValues() {
      // Given a database record with mixed-case Type and Category (ID = 1 from @SQL)
      // When that record is retrieved from the database
      LineItemEntity lineItem = entityManager.find(LineItemEntity.class, 1L);
      // Then the Enum values should be set correctly
      assertEquals(ItemType.EXPENSE, lineItem.getType());
      assertEquals(Category.FOOD, lineItem.getCategory());
    }

    @Test
    void shouldSortTransactionsByDateAndAmount() {
      // Given a LineItemEntity
      LineItemEntity mockLineItem = new LineItemEntity();
      mockLineItem.setBudgetDate(YearMonth.now());
      // Name the Line Item the class and method name to try to guarantee uniqueness
      mockLineItem.setName("LineItemEntityIT.shouldSortTransactions");
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
    void shouldNotSaveNullName() {
      // Given a LineItemEntity with a null name
      LineItemEntity lineItemEntity = createLineItemEntity("Groceries", YearMonth.now(), Category.FOOD);
      lineItemEntity.setName(null);
      // When the entity is persisted
      // Then a ConstraintViolationException should be thrown
      ConstraintViolationException exception =
          assertThrows(ConstraintViolationException.class, () -> entityManager.persistAndFlush(lineItemEntity));
      // And the exception message should contain the following:
      assertTrue(exception.getMessage().contains("propertyPath=name"));
      assertTrue(exception.getMessage().contains("interpolatedMessage='must not be blank'"));
    }

    @Test
    void shouldNotSaveWhiteSpaceName() {
      // Given a LineItemEntity with a name that contains only whitespace
      LineItemEntity lineItemEntity = createLineItemEntity("Groceries", YearMonth.now(), Category.FOOD);
      lineItemEntity.setName(" ");
      // When the entity is persisted
      // Then a ConstraintViolationException should be thrown
      ConstraintViolationException exception =
          assertThrows(ConstraintViolationException.class, () -> entityManager.persistAndFlush(lineItemEntity));
      // And the exception message should contain the following:
      assertTrue(exception.getMessage().contains("propertyPath=name"));
      assertTrue(exception.getMessage().contains("interpolatedMessage='must not be blank'"));
    }

    @Test
    void shouldEnforceUniqueConstraint() {
      // Given two entities with the same name, budgetDate, and category
      LineItemEntity lineItem1 = createLineItemEntity("name", YearMonth.now(), Category.FOOD);
      LineItemEntity lineItem2 = createLineItemEntity("name", YearMonth.now(), Category.FOOD);
      // When the first entity is persisted, no exceptions should be thrown
      assertDoesNotThrow(() -> entityManager.persistAndFlush(lineItem1));
      // And when the second entity is saved, a PersistenceException should be thrown
      PersistenceException exception =
          assertThrows(PersistenceException.class, () -> entityManager.persistAndFlush(lineItem2));
      // And the exception should contain the following
      assertTrue(exception.getCause().getCause().getMessage()
          .contains("duplicate key value violates unique constraint \"unique_insensitive_name_category_date_idx\""));
    }

    @Test
    void shouldEnforceUniqueConstraintEvenWhenMixedCase() {
      // Given two entities with the different mIxEdCaSe names but the same budgetDate and category
      LineItemEntity lineItem1 = createLineItemEntity("MiXeDcAsE", YearMonth.now(), Category.FOOD);
      LineItemEntity lineItem2 = createLineItemEntity("mIxEdCaSe", YearMonth.now(), Category.FOOD);
      // When the first entity is persisted, no exceptions should be thrown
      assertDoesNotThrow(() -> entityManager.persistAndFlush(lineItem1));
      // And when the second entity is saved, a PersistenceException should be thrown
      PersistenceException exception =
          assertThrows(PersistenceException.class, () -> entityManager.persistAndFlush(lineItem2));
      // And the exception should contain the following
      assertTrue(exception.getCause().getCause().getMessage()
          .contains("duplicate key value violates unique constraint \"unique_insensitive_name_category_date_idx\""));
    }

    @Test
    void shouldAllowTwoItemsToHaveSameNameIfDifferentCategory() {
      // Given two entities with the same name, budgetDate but different categories
      LineItemEntity lineItem1 = createLineItemEntity("Insurance", YearMonth.now(), Category.HEALTH);
      LineItemEntity lineItem2 = createLineItemEntity("Insurance", YearMonth.now(), Category.HOUSING);
      // When the first entity is saved, no exceptions should be thrown
      assertDoesNotThrow(() -> entityManager.persistAndFlush(lineItem1));
      // And when the second entity is saved, no exceptions should be thrown
      assertDoesNotThrow(() -> entityManager.persistAndFlush(lineItem2));
    }
  }

  private LineItemEntity createLineItemEntity(String name, YearMonth budgetDate, Category category) {
    LineItemEntity lineItemEntity = new LineItemEntity();
    lineItemEntity.setType(ItemType.EXPENSE);
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