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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { TransactionMapperImpl.class })
class TransactionMapperIT {
  @Autowired
  private TransactionMapper mapper;

  Instant lastModifiedAt = Instant.parse("2022-09-21T23:31:04.206157Z");

  @Test
  void mapRequestToDomain() {
    // Given a request object
    TransactionRequest request = new TransactionRequest(
        LocalDate.now(),
        "Merchant",
        2500,
        1L,
        "description");
    // And an expected domain object
    Transaction expectedDomain = Transaction.builder(
            LocalDate.now(),
            "Merchant",
            2500)
        .lineItemId(1L)
        .description("description")
        .build();

    // When the request is mapped to a domain object
    Transaction returned = mapper.mapRequestToDomain(request);

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
    TransactionEntity returned = mapper.mapDomainToEntity(domain);

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
    Transaction returned = mapper.mapEntityToDomain(entity);

    // Then the returned domain should equal the expected domain object
    assertEquals(expectedDomain, returned);
    // And have the same values
    assertThat(expectedDomain)
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
    TransactionResponse returned = mapper.mapDomainToResponse(domain);

    // Then the returned domain should equal the expected domain object
    assertEquals(expectedResponse, returned);
    // And have the same values
    assertThat(expectedResponse)
        .usingRecursiveComparison()
        .isEqualTo(returned);
  }
}
