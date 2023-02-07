package me.seantwiehaus.zbbp.mapper;

import me.seantwiehaus.zbbp.dao.entity.LineItemEntity;
import me.seantwiehaus.zbbp.dao.entity.TransactionEntity;
import me.seantwiehaus.zbbp.domain.Category;
import me.seantwiehaus.zbbp.domain.LineItem;
import me.seantwiehaus.zbbp.domain.Transaction;
import me.seantwiehaus.zbbp.dto.request.LineItemRequest;
import me.seantwiehaus.zbbp.dto.response.LineItemResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Instant;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { LineItemMapperImpl.class, TransactionMapperImpl.class })
class LineItemMapperIT {
  @Autowired
  private LineItemMapper mapper;

  Instant lastModifiedAt = Instant.parse("2022-09-21T23:31:04.206157Z");

  @Test
  void mapRequestToDomain() {
    // Given a request object
    LineItemRequest request = new LineItemRequest(
            YearMonth.now(),
            "Name",
            120000,
            Category.FOOD,
            "description");
    // And an expected domain object
    LineItem expectedDomain = LineItem.builder(
                    YearMonth.now(),
                    "Name",
                    120000,
                    Category.FOOD)
            .description("description")
            .build();

    // When the request is mapped to a domain object
    LineItem returned = mapper.mapToDomain(request);

    // Then the returned domain should equal the expected domain object
    assertEquals(expectedDomain, returned);
    // And have the same values
    assertThat(expectedDomain)
            .usingRecursiveComparison()
            .isEqualTo(returned);
  }

  @Test
  void mapDomainToEntity() {
    // Given a domain object
    LineItem domain = LineItem.builder(
                    YearMonth.now(),
                    "Name",
                    120000,
                    Category.FOOD)
            .description("description")
            // With a Transaction domain object
            .transactions(List.of(Transaction.builder(
                            LocalDate.now(),
                            "Merchant",
                            2500)
                    .build()))
            .build();
    // And an expected entity object
    LineItemEntity expectedEntity = LineItemEntity.builder()
            .budgetDate(YearMonth.now())
            .name("Name")
            .plannedAmount(120000)
            .category(Category.FOOD)
            .description("description")
            // With a TransactionEntity object
            .transactions(List.of(TransactionEntity.builder()
                    .date(LocalDate.now())
                    .merchant("Merchant")
                    .amount(2500)
                    .build()))
            .build();

    // When the domain is mapped to an entity
    LineItemEntity returned = mapper.mapToEntity(domain);

    // Then the returned domain should equal the expected domain object
    assertEquals(expectedEntity, returned);
    // And have the same values
    assertThat(expectedEntity)
            .usingRecursiveComparison()
            .isEqualTo(returned);
  }

  @Test
  void mapEntityToDomain() {
    // Given an entity object
    LineItemEntity entity = LineItemEntity.builder()
            .id(1L)
            .budgetDate(YearMonth.now())
            .name("Name")
            .plannedAmount(120000)
            .category(Category.FOOD)
            .description("description")
            .lastModifiedAt(lastModifiedAt)
            // With a TransactionEntity
            .transactions(List.of(TransactionEntity.builder()
                    .date(LocalDate.now())
                    .merchant("Merchant")
                    .amount(2500)
                    .build()))
            .build();
    // And an expected domain object
    LineItem expectedDomain = LineItem.builder(
                    YearMonth.now(),
                    "Name",
                    120000,
                    Category.FOOD)
            .id(1L)
            .description("description")
            .lastModifiedAt(lastModifiedAt)
            // With a Transaction domain object
            .transactions(List.of(Transaction.builder(
                            LocalDate.now(),
                            "Merchant",
                            2500)
                    .build()))
            .build();

    // When the request is mapped to a domain object
    LineItem returned = mapper.mapToDomain(entity);

    // Then the returned domain should equal the expected domain object
    assertEquals(expectedDomain, returned);
    // And have the same values
    assertThat(expectedDomain)
            .usingRecursiveComparison()
            .isEqualTo(returned);
  }

  @Test
  void mapEntitiesToDomains() {
    // Given a list of entity objects
    LineItemEntity entity1 = LineItemEntity.builder()
            .id(1L)
            .budgetDate(YearMonth.now())
            .name("Name")
            .plannedAmount(120000)
            .category(Category.FOOD)
            .description("description")
            .lastModifiedAt(lastModifiedAt)
            .build();
    LineItemEntity entity2 = LineItemEntity.builder()
            .id(2L)
            .budgetDate(YearMonth.now())
            .name("Name")
            .plannedAmount(120000)
            .category(Category.FOOD)
            .description("description")
            .lastModifiedAt(lastModifiedAt)
            .build();
    List<LineItemEntity> entities = List.of(entity1, entity2);
    // And a list of expected domain objects
    LineItem expectedDomain1 = LineItem.builder(
                    YearMonth.now(),
                    "Name",
                    120000,
                    Category.FOOD)
            .id(1L)
            .description("description")
            .lastModifiedAt(lastModifiedAt)
            .build();
    LineItem expectedDomain2 = LineItem.builder(
                    YearMonth.now(),
                    "Name",
                    120000,
                    Category.FOOD)
            .id(2L)
            .description("description")
            .lastModifiedAt(lastModifiedAt)
            .build();
    List<LineItem> expectedDomains = List.of(expectedDomain1, expectedDomain2);

    // When the request is mapped to a domain object
    List<LineItem> returned = mapper.mapToDomains(entities);

    // Then the returned domain objects should equal the expected domain objects
    assertThat(expectedDomains)
            .usingRecursiveComparison()
            .isEqualTo(returned);
  }

  @Test
  void mapDomainToResponse() {
    // Given a domain object
    LineItem domain = LineItem.builder(
                    YearMonth.now(),
                    "Name",
                    120000,
                    Category.FOOD)
            .id(1L)
            .description("description")
            .lastModifiedAt(lastModifiedAt)
            .build();
    // And an expected response object
    LineItemResponse expectedResponse = new LineItemResponse(
            1L,
            YearMonth.now(),
            "Name",
            120000,
            Category.FOOD,
            "description",
            lastModifiedAt,
            0,
            0.0,
            120000,
            List.of());

    // When the domain is mapped to a response object
    LineItemResponse returned = mapper.mapToResponse(domain);

    // Then the returned domain should equal the expected domain object
    assertEquals(expectedResponse, returned);
    // And have the same values
    assertThat(expectedResponse)
            .usingRecursiveComparison()
            .isEqualTo(returned);
  }
}
