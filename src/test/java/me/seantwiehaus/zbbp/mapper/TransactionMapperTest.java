package me.seantwiehaus.zbbp.mapper;

import me.seantwiehaus.zbbp.dao.entity.TransactionEntity;
import me.seantwiehaus.zbbp.domain.Transaction;
import me.seantwiehaus.zbbp.dto.request.TransactionRequest;
import me.seantwiehaus.zbbp.dto.response.TransactionResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { TransactionMapper.class })
class TransactionMapperTest {
  @Autowired
  private TransactionMapper mapper;

  Instant lastModifiedAt = Instant.parse("2022-09-21T23:31:04.206157Z");

  @Test
  void mapRequestToDomain() {
    // Given a request object
    TransactionRequest request = new TransactionRequest(
            LocalDate.now(),
            "Merchant",
            25.00,
            1L,
            "description");
    // And an expected domain object
    Transaction expectedDomain = Transaction.builder(
                    LocalDate.now(),
                    "Merchant",
                    2500) // converted to cents
            .lineItemId(1L)
            .description("description")
            .build();

    // When the request is mapped to a domain object
    Transaction returned = mapper.mapToDomain(request);

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
    Transaction domain = Transaction.builder(
                    LocalDate.now(),
                    "Merchant",
                    2500)
            .id(1L)
            .lineItemId(1L)
            .description("description")
            .lastModifiedAt(lastModifiedAt)
            .build();
    // And an expected entity object
    TransactionEntity expectedEntity = TransactionEntity.builder()
            .id(1L)
            .date(LocalDate.now())
            .merchant("Merchant")
            .amount(2500)
            .lineItemId(1L)
            .description("description")
            .lastModifiedAt(lastModifiedAt)
            .build();

    // When the domain is mapped to an entity
    TransactionEntity returned = mapper.mapToEntity(domain);

    // Then the returned domain should equal the expected domain object
    assertEquals(expectedEntity, returned);
    // And have the same values
    assertThat(expectedEntity)
            .usingRecursiveComparison()
            .isEqualTo(returned);
  }

  @Test
  void mapDomainsToEntities() {
    // Given a list of domain objects
    Transaction domain1 = Transaction.builder(
                    LocalDate.now(),
                    "Merchant",
                    2500)
            .id(1L)
            .lineItemId(1L)
            .description("description")
            .lastModifiedAt(lastModifiedAt)
            .build();
    Transaction domain2 = Transaction.builder(
                    LocalDate.now(),
                    "Merchant2",
                    2200)
            .id(2L)
            .lineItemId(2L)
            .description("description2")
            .lastModifiedAt(lastModifiedAt)
            .build();
    List<Transaction> domains = List.of(domain1, domain2);
    // And a list expected entity objects
    TransactionEntity expectedEntity1 = TransactionEntity.builder()
            .id(1L)
            .date(LocalDate.now())
            .merchant("Merchant")
            .amount(2500)
            .lineItemId(1L)
            .description("description")
            .lastModifiedAt(lastModifiedAt)
            .build();
    TransactionEntity expectedEntity2 = TransactionEntity.builder()
            .id(2L)
            .date(LocalDate.now())
            .merchant("Merchant2")
            .amount(2200)
            .lineItemId(2L)
            .description("description2")
            .lastModifiedAt(lastModifiedAt)
            .build();
    List<TransactionEntity> expectedEntities = List.of(expectedEntity1, expectedEntity2);

    // When the domains are mapped to entities
    List<TransactionEntity> returned = mapper.mapToEntities(domains);

    // Then the list of returned domain should equal the list of expected domain objects
    assertEquals(expectedEntities, returned);
    // And have the same values
    assertThat(expectedEntities)
            .usingRecursiveComparison()
            .isEqualTo(returned);
  }

  @Test
  void mapEntityToDomain() {
    // Given an entity object
    TransactionEntity entity = TransactionEntity.builder()
            .id(1L)
            .date(LocalDate.now())
            .merchant("Merchant")
            .amount(2500)
            .lineItemId(1L)
            .description("description")
            .lastModifiedAt(lastModifiedAt)
            .build();
    // And an expected domain object
    Transaction expectedDomain = Transaction.builder(
                    LocalDate.now(),
                    "Merchant",
                    2500)
            .id(1L)
            .lineItemId(1L)
            .description("description")
            .lastModifiedAt(lastModifiedAt)
            .build();

    // When the request is mapped to a domain object
    Transaction returned = mapper.mapToDomain(entity);

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
    TransactionEntity entity1 = TransactionEntity.builder()
            .id(1L)
            .date(LocalDate.now())
            .merchant("Merchant")
            .amount(2500)
            .lineItemId(1L)
            .description("description")
            .lastModifiedAt(lastModifiedAt)
            .build();
    TransactionEntity entity2 = TransactionEntity.builder()
            .id(2L)
            .date(LocalDate.now())
            .merchant("Merchant2")
            .amount(2200)
            .lineItemId(2L)
            .description("description2")
            .lastModifiedAt(lastModifiedAt)
            .build();
    List<TransactionEntity> entities = List.of(entity1, entity2);
    // And a list of expected domain objects
    Transaction expectedDomain1 = Transaction.builder(
                    LocalDate.now(),
                    "Merchant",
                    2500)
            .id(1L)
            .lineItemId(1L)
            .description("description")
            .lastModifiedAt(lastModifiedAt)
            .build();
    Transaction expectedDomain2 = Transaction.builder(
                    LocalDate.now(),
                    "Merchant2",
                    2200)
            .id(2L)
            .lineItemId(2L)
            .description("description2")
            .lastModifiedAt(lastModifiedAt)
            .build();
    List<Transaction> expectedDomains = List.of(expectedDomain1, expectedDomain2);

    // When the entities are mapped to domain objects
    List<Transaction> returned = mapper.mapToDomains(entities);

    // Then the list of returned domain objects should equal the list of expected domain objects
    assertEquals(expectedDomains, returned);
    // And have the same values
    assertThat(expectedDomains)
            .usingRecursiveComparison()
            .isEqualTo(returned);
  }

  @Test
  void mapDomainToResponse() {
    // Given a domain object
    Transaction domain = Transaction.builder(
                    LocalDate.now(),
                    "Merchant",
                    2500)
            .id(1L)
            .lineItemId(1L)
            .description("description")
            .lastModifiedAt(lastModifiedAt)
            .build();
    // And an expected response object
    TransactionResponse expectedResponse = new TransactionResponse(
            1L,
            LocalDate.now(),
            "Merchant",
            2500,
            1L,
            "description",
            lastModifiedAt);

    // When the domain is mapped to a response object
    TransactionResponse returned = mapper.mapToResponse(domain);

    // Then the returned domain should equal the expected domain object
    assertEquals(expectedResponse, returned);
    // And have the same values
    assertThat(expectedResponse)
            .usingRecursiveComparison()
            .isEqualTo(returned);
  }

  @Test
  void mapDomainsToResponses() {
    // Given a list of domain objects
    Transaction domain1 = Transaction.builder(
                    LocalDate.now(),
                    "Merchant",
                    2500)
            .id(1L)
            .lineItemId(1L)
            .description("description")
            .lastModifiedAt(lastModifiedAt)
            .build();
    Transaction domain2 = Transaction.builder(
                    LocalDate.now(),
                    "Merchant2",
                    2200)
            .id(2L)
            .lineItemId(2L)
            .description("description2")
            .lastModifiedAt(lastModifiedAt)
            .build();
    List<Transaction> domains = List.of(domain1, domain2);
    // And a list of expected response objects
    TransactionResponse expectedResponse1 = new TransactionResponse(
            1L,
            LocalDate.now(),
            "Merchant",
            2500,
            1L,
            "description",
            lastModifiedAt);
    TransactionResponse expectedResponse2 = new TransactionResponse(
            2L,
            LocalDate.now(),
            "Merchant2",
            2200,
            2L,
            "description2",
            lastModifiedAt);
    List<TransactionResponse> expectedResponse = List.of(expectedResponse1, expectedResponse2);

    // When the domain objects are mapped to response objects
    List<TransactionResponse> returned = mapper.mapToResponses(domains);

    // Then the returned domain should equal the expected domain object
    assertEquals(expectedResponse, returned);
    // And have the same values
    assertThat(expectedResponse)
            .usingRecursiveComparison()
            .isEqualTo(returned);
  }
}
