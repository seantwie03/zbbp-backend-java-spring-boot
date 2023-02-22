package me.seantwiehaus.zbbp.mapper;

import me.seantwiehaus.zbbp.dao.entity.TransactionEntity;
import me.seantwiehaus.zbbp.domain.Transaction;
import me.seantwiehaus.zbbp.dto.request.TransactionRequest;
import me.seantwiehaus.zbbp.dto.response.TransactionResponse;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Instant;
import java.time.LocalDate;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { TransactionMapperImpl.class })
class TransactionMapperIT {
  @Autowired
  private TransactionMapper mapper;

  Instant lastModifiedAt = Instant.parse("2022-09-21T23:31:04.206157Z");

  @Nested
  class MapRequestToDomain {
    @Test
    void correctlyMapsAllFields() {
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

    @ParameterizedTest(name = "{index} => {0} dollars converts to {1} cents")
    @MethodSource
    void convertsDollarsToCents(double dollars, int cents) {
      // Given a request object
      TransactionRequest request = new TransactionRequest(
              LocalDate.now(),
              "Merchant",
              dollars,
              1L,
              "description");
      // And an expected domain object
      Transaction expectedDomain = Transaction.builder(
                      LocalDate.now(),
                      "Merchant",
                      cents) // converted to cents
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

    private static Stream<Arguments> convertsDollarsToCents() {
      return Stream.of(
              Arguments.of(0.000001, 0),
              Arguments.of(0.001, 0),
              Arguments.of(0.00, 0),
              Arguments.of(0.01, 1),
              Arguments.of(0.1, 10),
              Arguments.of(0.10, 10),
              Arguments.of(1, 100),
              Arguments.of(1.00, 100),
              Arguments.of(1.0000000, 100)
      );
    }
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
}
