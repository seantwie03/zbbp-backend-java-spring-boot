package me.seantwiehaus.zbbp.service;

import me.seantwiehaus.zbbp.dao.entity.TransactionEntity;
import me.seantwiehaus.zbbp.dao.repository.TransactionRepository;
import me.seantwiehaus.zbbp.domain.Transaction;
import me.seantwiehaus.zbbp.exception.ResourceConflictException;
import me.seantwiehaus.zbbp.exception.ResourceNotFoundException;
import me.seantwiehaus.zbbp.mapper.TransactionMapper;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {
  @Mock
  TransactionRepository repository;
  @Mock
  TransactionMapper mapper;
  @InjectMocks
  TransactionService service;

  @Captor
  private ArgumentCaptor<TransactionEntity> entityCaptor = ArgumentCaptor.forClass(TransactionEntity.class);

  private final Long id = 1L;
  Instant ifUnmodifiedSince = Instant.parse("2022-09-21T23:31:04.206157Z");

  @Nested
  class GetAllBetween {
    private final LocalDate startDate = LocalDate.of(2022, 1, 5);
    private final LocalDate endDate = LocalDate.of(2022, 1, 7);

    @Test
    void callMapperWithCorrectEntity() {
      // Given one entity returned from the repository
      TransactionEntity entityFromRepo = createEntity().date(startDate).build();
      when(repository.findAllByDateBetweenOrderByDateDescAmountDesc(startDate, endDate))
          .thenReturn(List.of(entityFromRepo));

      // When the method under test is called
      service.getAllBetween(startDate, endDate);

      // Then the mapper should be called one time
      verify(mapper, times(1)).mapEntityToDomain(entityCaptor.capture());
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
      TransactionEntity entity1 = createEntity().id(1L).date(startDate).build();
      TransactionEntity entity2 = createEntity().id(2L).date(endDate).build();
      List<TransactionEntity> orderedByDate = List.of(entity1, entity2);
      when(repository.findAllByDateBetweenOrderByDateDescAmountDesc(startDate, endDate))
          .thenReturn(orderedByDate);

      // When the method under test is called
      service.getAllBetween(startDate, endDate);

      // Then the mapper should be called two times
      verify(mapper, times(2)).mapEntityToDomain(entityCaptor.capture());
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
      when(repository.findById(id))
          .thenReturn(Optional.empty());

      // When the method under test is called
      // Then a ResourceNotFoundException should be thrown
      assertThrows(ResourceNotFoundException.class, () -> service.findById(id));
    }

    @Test
    void callsMapperWithCorrectEntity() {
      // Given one entity returned from the repository
      TransactionEntity entityFromRepo = createEntity().id(id).build();
      when(repository.findById(id))
          .thenReturn(Optional.of(entityFromRepo));

      // When the method under test is called
      service.findById(id);

      // Then the mapper should be called one time
      verify(mapper, times(1)).mapEntityToDomain(entityCaptor.capture());
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
      Transaction parameter = createDomain().id(null).lastModifiedAt(null).build();
      // And that parameter mapper to an entity
      TransactionEntity entityFromParameter = createEntity().id(null).lastModifiedAt(null).build();
      when(mapper.mapDomainToEntity(parameter))
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
      Transaction parameter = createDomain().id(null).lastModifiedAt(null).build();
      // And an entity returned from the repository.save() with ID and LastModifiedAt values
      TransactionEntity entityFromSave = createEntity().id(1L).lastModifiedAt(Instant.now()).build();
      when(repository.save(any()))
          .thenReturn(entityFromSave);

      // When the method under test is called
      service.create(parameter);

      // Then the mapper should be called one time
      verify(mapper, times(1)).mapEntityToDomain(entityCaptor.capture());
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
      Transaction parameter = createDomain().id(-1L).build();
      // And an empty Optional returned from the repository (entity not found by ID)
      when(repository.findById(-1L))
          .thenReturn(Optional.empty());

      // When the method under test is called
      // Then a ResourceNotFoundException should be thrown
      assertThrows(ResourceNotFoundException.class, () -> service.update(-1L, ifUnmodifiedSince, parameter));
    }

    @Test
    void throwsConflictExceptionWhenIfUnmodifiedSinceIsBeforeEntityLastModifiedAt() {
      // Given a parameter
      Transaction parameter = createDomain().build();
      // And a ifUnmodifiedSince value (in top-level class)
      // And an entity from the repository with a lastModifiedAt value that is after the ifUnmodifiedSince value
      TransactionEntity entityFromRepo = createEntity().lastModifiedAt(ifUnmodifiedSince.plusSeconds(1)).build();
      when(repository.findById(id))
          .thenReturn(Optional.of(entityFromRepo));

      // When the method under test is called
      // Then a ResourceConflictException should be thrown
      assertThrows(ResourceConflictException.class, () -> service.update(id, ifUnmodifiedSince, parameter));
    }

    @Test
    void callsRepositorySaveWithCorrectPropertyValues() {
      // Given an existing entity from the repository with all values set
      TransactionEntity entityFromDb = createEntity()
          .id(id)
          .date(LocalDate.now().minusDays(1))
          .merchant("Merchant")
          .amount(2500)
          .lineItemId(1L)
          .lastModifiedAt(ifUnmodifiedSince)
          .build();
      when(repository.findById(id))
          .thenReturn(Optional.of(entityFromDb));
      // And a valid ifUnmodifiedSince (in top-level class)
      // And a Transaction parameter with updated values
      Transaction parameter = new Transaction(
          id,
          LocalDate.now(),
          "Updated Merchant",
          2600,
          2L,
          "Updated Description",
          ifUnmodifiedSince);

      // When the method under test is called
      service.update(id, ifUnmodifiedSince, parameter);

      // Then the repository save method should be called one time
      verify(repository, times(1)).save(entityCaptor.capture());
      // And the values passed to repository.save() should be the same as the values from the parameter
      assertEquals(parameter.id(), entityCaptor.getValue().getId());
      assertEquals(parameter.date(), entityCaptor.getValue().getDate());
      assertEquals(parameter.merchant(), entityCaptor.getValue().getMerchant());
      assertEquals(parameter.amount(), entityCaptor.getValue().getAmount());
      assertEquals(parameter.lineItemId(), entityCaptor.getValue().getLineItemId());
      assertEquals(parameter.description(), entityCaptor.getValue().getDescription());
      assertEquals(parameter.lastModifiedAt(), entityCaptor.getValue().getLastModifiedAt());
    }

    @Test
    void callsMapperWithCorrectEntity() {
      // Given an entity from the repository
      when(repository.findById(id))
          .thenReturn(Optional.of(createEntity().build()));
      // And an ifUnmodifiedSince value that matches (in top-level class)
      // And an entity returned from the repository save method
      TransactionEntity entityFromSave = createEntity().build();
      when(repository.save(any(TransactionEntity.class)))
          .thenReturn(entityFromSave);

      // When the method under test is called
      service.update(id, ifUnmodifiedSince, createDomain().id(id).build());

      // Then the mapper should be called one time
      verify(mapper, times(1)).mapEntityToDomain(entityCaptor.capture());
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
      TransactionEntity entityFromRepo = createEntity().build();
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

  private TransactionEntity.TransactionEntityBuilder<?, ?> createEntity() {
    return TransactionEntity
        .builder()
        .id(1L)
        .date(LocalDate.now())
        .merchant("Merchant")
        .amount(2500)
        .description("Description")
        .lineItemId(1L)
        .lastModifiedAt(ifUnmodifiedSince);
  }

  private Transaction.TransactionBuilder createDomain() {
    return Transaction.builder(LocalDate.now(), "Merchant", 2500)
        .id(1L)
        .description("Description")
        .lineItemId(1L)
        .lastModifiedAt(ifUnmodifiedSince);
  }
}
