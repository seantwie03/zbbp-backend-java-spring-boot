package me.seantwiehaus.zbbp.service;

import me.seantwiehaus.zbbp.dao.entity.LineItemEntity;
import me.seantwiehaus.zbbp.dao.repository.LineItemRepository;
import me.seantwiehaus.zbbp.domain.Category;
import me.seantwiehaus.zbbp.domain.LineItem;
import me.seantwiehaus.zbbp.exception.PreconditionFailedException;
import me.seantwiehaus.zbbp.exception.ResourceNotFoundException;
import me.seantwiehaus.zbbp.mapper.LineItemMapper;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LineItemServiceTest {
  @Mock
  LineItemRepository repository;
  @Mock
  LineItemMapper mapper;
  @InjectMocks
  LineItemService service;

  @Captor
  private ArgumentCaptor<LineItemEntity> entityCaptor = ArgumentCaptor.forClass(LineItemEntity.class);

  private final Long id = 1L;
  Instant ifUnmodifiedSince = Instant.parse("2022-09-21T23:31:04.206157Z");

  @Nested
  class GetAllBetween {
    private final YearMonth startDate = YearMonth.of(2022, 1);
    private final YearMonth endDate = YearMonth.of(2022, 2);

    @Test
    void callMapperWithCorrectEntity() {
      // Given one entity returned from the repository
      LineItemEntity entityFromRepo = createEntity().budgetDate(startDate).build();
      when(repository.findAllByBudgetDateBetweenOrderByBudgetDateDescCategoryAsc(startDate, endDate))
              .thenReturn(List.of(entityFromRepo));

      // When the method under test is called
      service.getAllBetween(startDate, endDate);

      // Then the mapper should be called one time
      verify(mapper, times(1)).mapToDomain(entityCaptor.capture());
      // And the entity passed to the mapper should be the same as the one returned from the repository
      assertEquals(entityFromRepo, entityCaptor.getValue());
      // And all the properties should be the same
      assertThat(entityFromRepo)
              .usingRecursiveComparison()
              .isEqualTo(entityCaptor.getValue());
    }

    @Test
    void callsMapperWithEntitiesInOrder() {
      // Given two entities returned from the repository in order by date
      LineItemEntity entity1 = createEntity().id(1L).budgetDate(startDate).build();
      LineItemEntity entity2 = createEntity().id(2L).budgetDate(endDate).build();
      List<LineItemEntity> orderedByDate = List.of(entity1, entity2);
      when(repository.findAllByBudgetDateBetweenOrderByBudgetDateDescCategoryAsc(startDate, endDate))
              .thenReturn(orderedByDate);

      // When the method under test is called
      service.getAllBetween(startDate, endDate);

      // Then the mapper should be called two times
      verify(mapper, times(2)).mapToDomain(entityCaptor.capture());
      // And the entities passed to the mapper should be in the same order as the ones returned from the repository
      assertEquals(orderedByDate.get(0), entityCaptor.getAllValues().get(0));
      assertEquals(orderedByDate.get(1), entityCaptor.getAllValues().get(1));
      // And all the properties should be the same
      assertThat(entity1)
              .usingRecursiveComparison()
              .isEqualTo(entityCaptor.getAllValues().get(0));
      assertThat(entity2)
              .usingRecursiveComparison()
              .isEqualTo(entityCaptor.getAllValues().get(1));
    }
  }

  @Nested
  class FindById {
    @Test
    void throwsResourceNotFoundExceptionWhenNotFoundById() {
      // Given an empty Optional returned from the repository (entity not found by ID)
      when(repository.findLineItemEntityById(id))
              .thenReturn(Optional.empty());

      // When the method under test is called
      // Then a ResourceNotFoundException should be thrown
      assertThrows(ResourceNotFoundException.class, () -> service.getById(id));
    }

    @Test
    void callsMapperWithCorrectEntity() {
      // Given one entity returned from the repository
      LineItemEntity entityFromRepo = createEntity().id(id).build();
      when(repository.findLineItemEntityById(id))
              .thenReturn(Optional.of(entityFromRepo));

      // When the method under test is called
      service.getById(id);

      // Then the mapper should be called one time
      verify(mapper, times(1)).mapToDomain(entityCaptor.capture());
      // And the entity passed to the mapper should be the same as the one returned from the repository
      assertEquals(entityFromRepo, entityCaptor.getValue());
      // And all the properties should be the same
      assertThat(entityFromRepo)
              .usingRecursiveComparison()
              .isEqualTo(entityCaptor.getValue());
    }
  }

  @Nested
  class Create {
    @Test
    void callsRepositorySaveWithCorrectEntity() {
      // Given a parameter with no ID or LastModifiedAt values
      LineItem parameter = createDomain().id(null).lastModifiedAt(null).build();
      // And that parameter mapper to an entity
      LineItemEntity entityFromParameter = createEntity().id(null).lastModifiedAt(null).build();
      when(mapper.mapToEntity(parameter))
              .thenReturn(entityFromParameter);

      // When the method under test is called
      service.create(parameter);

      // Then the repository save method should be called one time
      verify(repository, times(1)).save(entityCaptor.capture());
      // And the entity passed to the repository should be the same as the one returned from the mapper
      assertEquals(entityFromParameter, entityCaptor.getValue());
      // And all the properties should be the same
      assertThat(entityFromParameter)
              .usingRecursiveComparison()
              .isEqualTo(entityCaptor.getValue());
    }

    @Test
    void callsTransactionMapperWithCorrectEntity() {
      // Given a parameter with no ID or LastModifiedAt values
      LineItem parameter = createDomain().id(null).lastModifiedAt(null).build();
      // And an entity returned from the repository.save() with ID and LastModifiedAt values
      LineItemEntity entityFromSave = createEntity().id(1L).lastModifiedAt(Instant.now()).build();
      when(repository.save(any()))
              .thenReturn(entityFromSave);

      // When the method under test is called
      service.create(parameter);

      // Then the mapper should be called one time
      verify(mapper, times(1)).mapToDomain(entityCaptor.capture());
      // And the entity passed to the mapper should be the same as the one returned from the repository.save()
      assertEquals(entityFromSave, entityCaptor.getValue());
      // And all the properties should be the same
      assertThat(entityFromSave)
              .usingRecursiveComparison()
              .isEqualTo(entityCaptor.getValue());
    }
  }

  @Nested
  class Update {
    @Test
    void throwsResourceNotFoundExceptionWhenNotFoundById() {
      // Given a parameter with an invalid ID
      LineItem parameter = createDomain().id(-1L).build();
      // And an empty Optional returned from the repository (entity not found by ID)
      when(repository.findLineItemEntityById(-1L))
              .thenReturn(Optional.empty());

      // When the method under test is called
      // Then a ResourceNotFoundException should be thrown
      assertThrows(ResourceNotFoundException.class, () -> service.update(-1L, ifUnmodifiedSince, parameter));
    }

    @Test
    void throwsConflictExceptionWhenIfUnmodifiedSinceIsBeforeEntityLastModifiedAt() {
      // Given a parameter
      LineItem parameter = createDomain().build();
      // And a ifUnmodifiedSince value (in top-level class)
      // And an entity from the repository with a lastModifiedAt value that is after the ifUnmodifiedSince value
      LineItemEntity entityFromRepo = createEntity().lastModifiedAt(ifUnmodifiedSince.plusSeconds(1)).build();
      when(repository.findLineItemEntityById(id))
              .thenReturn(Optional.of(entityFromRepo));

      // When the method under test is called
      // Then a ResourceConflictException should be thrown
      assertThrows(PreconditionFailedException.class, () -> service.update(id, ifUnmodifiedSince, parameter));
    }

    @Test
    void callsRepositorySaveWithCorrectPropertyValues() {
      // Given an existing entity from the repository with all values set
      LineItemEntity entityFromDb = createEntity()
              .id(1L)
              .budgetDate(YearMonth.now().minusMonths(1))
              .name("Original " + UUID.randomUUID()) // UUID to ensure uniqueness
              .plannedAmount(120000)
              .category(Category.FOOD)
              .description("Description")
              .lastModifiedAt(ifUnmodifiedSince)
              .build();
      when(repository.findLineItemEntityById(id))
              .thenReturn(Optional.of(entityFromDb));
      // And a valid ifUnmodifiedSince (in top-level class)
      // And a LineItem parameter with updated values
      LineItem parameter = LineItem.builder(
                      YearMonth.now(),
                      "Updated " + UUID.randomUUID(),
                      100000,
                      Category.SAVINGS)
              .id(id)
              .description("Updated description")
              .build();

      // When the method under test is called
      service.update(id, ifUnmodifiedSince, parameter);

      // Then the repository save method should be called one time
      verify(repository, times(1)).save(entityCaptor.capture());
      // And the values passed to repository.save() should be the same as the values from the parameter
      assertEquals(parameter.id(), entityCaptor.getValue().getId());
      assertEquals(parameter.budgetDate(), entityCaptor.getValue().getBudgetDate());
      assertEquals(parameter.name(), entityCaptor.getValue().getName());
      assertEquals(parameter.plannedAmount(), entityCaptor.getValue().getPlannedAmount());
      assertEquals(parameter.category(), entityCaptor.getValue().getCategory());
      assertEquals(parameter.description(), entityCaptor.getValue().getDescription());
    }

    @Test
    void callsMapperWithCorrectEntity() {
      // Given an entity from the repository
      when(repository.findLineItemEntityById(id))
              .thenReturn(Optional.of(createEntity().build()));
      // And an ifUnmodifiedSince value that matches (in top-level class)
      // And an entity returned from the repository save method
      LineItemEntity entityFromSave = createEntity().build();
      when(repository.save(any(LineItemEntity.class)))
              .thenReturn(entityFromSave);

      // When the method under test is called
      service.update(id, ifUnmodifiedSince, createDomain().id(id).build());

      // Then the mapper should be called one time
      verify(mapper, times(1)).mapToDomain(entityCaptor.capture());
      // And the entity passed to the mapper should be the same as the one returned from the repository.save()
      assertEquals(entityFromSave, entityCaptor.getValue());
      // And all the properties should be the same
      assertThat(entityFromSave)
              .usingRecursiveComparison()
              .isEqualTo(entityCaptor.getValue());
    }
  }

  @Nested
  class Delete {
    @Test
    void throwsResourceNotFoundExceptionWhenNotFoundById() {
      // Given an empty Optional returned from the repository (entity not found by ID)
      when(repository.findById(-1L))
              .thenReturn(Optional.empty());

      // When the method under test is called
      // Then a ResourceNotFoundException should be thrown
      assertThrows(ResourceNotFoundException.class, () -> service.delete(-1L));
    }

    @Test
    void callsRepositoryDeleteWithCorrectEntity() {
      // Given an existing entity in the repository
      LineItemEntity entityFromRepo = createEntity().build();
      when(repository.findById(id))
              .thenReturn(Optional.of(entityFromRepo));

      // When the method under test is called
      service.delete(id);

      // Then the repository delete method should be called one time
      verify(repository, times(1)).delete(entityCaptor.capture());
      // And the entity passed to repository.delete() should be the same as the one returned from the repository
      assertEquals(entityFromRepo, entityCaptor.getValue());
      // And all the properties should be the same
      assertThat(entityFromRepo)
              .usingRecursiveComparison()
              .isEqualTo(entityCaptor.getValue());
    }
  }

  private LineItemEntity.LineItemEntityBuilder<?, ?> createEntity() {
    return LineItemEntity
            .builder()
            .id(1L)
            .budgetDate(YearMonth.now())
            .name("Groceries " + UUID.randomUUID()) // UUID to ensure uniqueness
            .plannedAmount(120000)
            .category(Category.FOOD)
            .description("Description")
            .lastModifiedAt(ifUnmodifiedSince);
  }

  private LineItem.LineItemBuilder createDomain() {
    return LineItem.builder(YearMonth.now(), "Groceries", 120000, Category.FOOD)
            .id(1L)
            .description("Description")
            .lastModifiedAt(ifUnmodifiedSince);
  }
}
