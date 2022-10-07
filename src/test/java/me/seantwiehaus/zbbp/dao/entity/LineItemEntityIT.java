package me.seantwiehaus.zbbp.dao.entity;

import me.seantwiehaus.zbbp.dao.repository.LineItemRepository;
import me.seantwiehaus.zbbp.domain.Category;
import me.seantwiehaus.zbbp.domain.ItemType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.TransactionSystemException;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LineItemEntityIT {
  @Autowired
  LineItemRepository lineItemRepository;

  LineItemEntity lineItemEntity;

  @BeforeEach
  void setup() {
    lineItemEntity = new LineItemEntity();
    lineItemEntity.setType(ItemType.EXPENSE);
    lineItemEntity.setBudgetDate(LocalDate.now().withDayOfMonth(1));
    lineItemEntity.setName("Groceries");
    lineItemEntity.setPlannedAmount(120000);
    lineItemEntity.setCategory(Category.FOOD);
  }

  @Test
  void shouldNotSaveNullName() {
    // Given a null budgetDate
    lineItemEntity.setName(null);
    // When the entity is saved
    // Then a TransactionSystemException should be thrown
    TransactionSystemException transactionSystemException =
        assertThrows(TransactionSystemException.class, () -> lineItemRepository.save(lineItemEntity));
    // And the TransactionSystemException should contain a ConstraintViolationException
    assertTrue(transactionSystemException.contains(ConstraintViolationException.class));
    Throwable innerException = transactionSystemException.getMostSpecificCause();
    // And the ConstraintViolationException should contain the following error messages
    assertTrue(innerException.getMessage().contains("propertyPath=name"));
    assertTrue(innerException.getMessage().contains("interpolatedMessage='must not be blank'"));
  }

  @Test
  void shouldNotSaveWhiteSpaceName() {
    // Given a name that contains only whitespace
    lineItemEntity.setName(" ");
    // When the entity is saved
    // Then a TransactionSystemException should be thrown
    TransactionSystemException transactionSystemException =
        assertThrows(TransactionSystemException.class, () -> lineItemRepository.save(lineItemEntity));
    // And the TransactionSystemException should contain a ConstraintViolationException
    assertTrue(transactionSystemException.contains(ConstraintViolationException.class));
    Throwable innerException = transactionSystemException.getMostSpecificCause();
    // And the ConstraintViolationException should contain the following error messages
    assertTrue(innerException.getMessage().contains("propertyPath=name"));
    assertTrue(innerException.getMessage().contains("interpolatedMessage='must not be blank'"));
  }

  @Test
  void shouldEnforceUniqueConstraint() {
    // Given two entities with the same name, budgetDate, and category
    LineItemEntity lineItem1 = new LineItemEntity();
    lineItem1.setName("The Name");
    lineItem1.setBudgetDate(LocalDate.now().withDayOfMonth(1));
    lineItem1.setCategory(Category.FOOD);
    LineItemEntity lineItem2 = new LineItemEntity();
    lineItem2.setName("The Name");
    lineItem2.setBudgetDate(LocalDate.now().withDayOfMonth(1));
    lineItem2.setCategory(Category.FOOD);
    // And the rest of the required fields
    lineItem1.setType(ItemType.EXPENSE);
    lineItem1.setPlannedAmount(120000);
    lineItem1.setBudgetDate(LocalDate.now());
    lineItem2.setType(ItemType.EXPENSE);
    lineItem2.setPlannedAmount(120000);
    lineItem2.setBudgetDate(LocalDate.now());
    // When the first entity is saved
    lineItemRepository.save(lineItem1);
    // And then the second entity is saved
    // Then a DataIntegrityViolationException should be thrown
    DataIntegrityViolationException violationException =
        assertThrows(DataIntegrityViolationException.class, () -> lineItemRepository.save(lineItem2));
    // And the exception should contain the following error message
    assertTrue(Objects.requireNonNull(violationException.getMessage())
        .contains("constraint [unique_insensitive_name_category_date_idx]"));
  }

  @Test
  void shouldEnforceUniqueConstraintEvenWhenMixedCase() {
    // Given two entities with the same mIxEdCaSe name, budgetDate, and category
    LineItemEntity lineItem1 = new LineItemEntity();
    lineItem1.setName("MiXeDcAsE");
    lineItem1.setBudgetDate(LocalDate.now().withDayOfMonth(1));
    lineItem1.setCategory(Category.FOOD);
    LineItemEntity lineItem2 = new LineItemEntity();
    lineItem2.setName("mIxEdCaSe");
    lineItem2.setBudgetDate(LocalDate.now().withDayOfMonth(1));
    lineItem2.setCategory(Category.FOOD);
    // And the rest of the required fields
    lineItem1.setType(ItemType.EXPENSE);
    lineItem1.setPlannedAmount(120000);
    lineItem1.setBudgetDate(LocalDate.now());
    lineItem2.setType(ItemType.EXPENSE);
    lineItem2.setPlannedAmount(120000);
    lineItem2.setBudgetDate(LocalDate.now());
    // When the first entity is saved
    lineItemRepository.save(lineItem1);
    // And then the second entity is saved
    // Then a DataIntegrityViolationException should be thrown
    DataIntegrityViolationException violationException =
        assertThrows(DataIntegrityViolationException.class, () -> lineItemRepository.save(lineItem2));
    // And the exception should contain the following error message
    assertTrue(Objects.requireNonNull(violationException.getMessage())
        .contains("constraint [unique_insensitive_name_category_date_idx]"));
  }

  @Test
  void shouldAllowTwoItemsToHaveSameNameIfDifferentCategory() {
    // Given two entities with the same name, budgetDate but different categories
    LineItemEntity lineItem1 = new LineItemEntity();
    lineItem1.setName("Insurance");
    lineItem1.setBudgetDate(LocalDate.now().withDayOfMonth(1));
    lineItem1.setCategory(Category.TRANSPORTATION);
    LineItemEntity lineItem2 = new LineItemEntity();
    lineItem2.setName("Insurance");
    lineItem2.setBudgetDate(LocalDate.now().withDayOfMonth(1));
    lineItem2.setCategory(Category.HOUSING);
    // And the rest of the required fields
    lineItem1.setType(ItemType.EXPENSE);
    lineItem1.setPlannedAmount(120000);
    lineItem1.setBudgetDate(LocalDate.now());
    lineItem2.setType(ItemType.EXPENSE);
    lineItem2.setPlannedAmount(120000);
    lineItem2.setBudgetDate(LocalDate.now());
    // When the first entity is saved
    lineItemRepository.save(lineItem1);
    // And then the second entity is saved successfully
    assertDoesNotThrow(() -> lineItemRepository.save(lineItem2));
  }

  @Test
  @Sql("insertMixedCaseTypeAndCategoryIntoLineItems.sql")
  void shouldRetrieveEnumsFromDatabaseWhenDatabaseContainsMixedCaseValues() {
    // Given a database record with mixed-case Type and Category (ID = 7878778 from @SQL)
    // When that record is retrieved from the database
    Optional<LineItemEntity> lineItem = lineItemRepository.findLineItemEntityById(7878778L);
    assertTrue(lineItem.isPresent());
    // Then the Enum values should be set correctly
    assertEquals(ItemType.EXPENSE, lineItem.get().getType());
    assertEquals(Category.FOOD, lineItem.get().getCategory());
  }

  @Test
  @Sql("insertLineItemWithTransactionsToTestSorting.sql")
  void shouldSortTransactionsByDateAndAmount() {
    // Given a LineItem record (7878779) with 3 transactions (6969669, 6969668, 6969667)
    // When that record is retrieved from the database
    Optional<LineItemEntity> lineItem = lineItemRepository.findLineItemEntityById(7878779L);
    assertTrue(lineItem.isPresent());
    assertEquals(3, lineItem.get().getTransactionEntities().size());
    // Then the Transactions should be sorted by Date then by Amount
    // 6969669 has the earliest date and should be first
    assertEquals(6969669L, lineItem.get().getTransactionEntities().get(0).getId());
    // The next two have the same date, but 6969668 has the higher amount and should be next
    assertEquals(6969668L, lineItem.get().getTransactionEntities().get(1).getId());
    // 6969667 has the lowest amount and should be last
    assertEquals(6969667L, lineItem.get().getTransactionEntities().get(2).getId());
  }
}
