package me.seantwiehaus.zbbp.controller;

import me.seantwiehaus.zbbp.domain.Transaction;
import me.seantwiehaus.zbbp.dto.response.TransactionResponse;
import me.seantwiehaus.zbbp.mapper.TransactionMapper;
import me.seantwiehaus.zbbp.service.TransactionService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.parallel.ExecutionMode.SAME_THREAD;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// Cannot run in parallel with other tests due to @MockBean
// https://docs.spring.io/spring-framework/docs/current/reference/html/testing.html#testcontext-parallel-test-execution
// https://stackoverflow.com/a/64628499/15513517
@Execution(SAME_THREAD)
@WebMvcTest(TransactionController.class)
class TransactionControllerIT {
  @MockBean
  TransactionService service;
  @MockBean
  TransactionMapper mapper;

  @Autowired
  MockMvc mockMvc;

  @Captor
  ArgumentCaptor<Transaction> domainCaptor = ArgumentCaptor.forClass(Transaction.class);

  private final Long id = 1L;
  private final Instant lastModifiedAt = Instant.parse("2022-09-21T23:31:04.206157Z");
  private final DateTimeFormatter rfc1123Formatter = DateTimeFormatter.RFC_1123_DATE_TIME.withZone(ZoneOffset.UTC);

  @Nested
  class GetAllTransactionsBetween {
    private final LocalDate defaultStartingDate = LocalDate.now().withDayOfMonth(1);
    private final LocalDate defaultEndingDate = LocalDate.now();

    @Test
    void callsServiceWithDefaultStartingAndEndingDateWhenNoneProvided() throws Exception {
      // Given a request with no starting or ending date
      // When the request is made
      mockMvc.perform(get("/transactions"));

      // Then the service should be called one time with the default starting and ending dates
      verify(service, times(1)).getAllBetween(defaultStartingDate, defaultEndingDate);
    }

    @Test
    void returns400WhenStartingDateIsNotCorrectlyFormatted() throws Exception {
      // Given a request with an invalid starting date
      String badStartingDate = "20210921";

      // When the request is made
      mockMvc.perform(get("/transactions?startingDate=%s".formatted(badStartingDate)))
              // Then the response should be a 400
              .andExpect(status().isBadRequest());
      // And the service should not be called
      verify(service, never()).getAllBetween(any(), any());
    }

    @Test
    void returns400WhenEndingDateIsNotCorrectlyFormatted() throws Exception {
      // Given a request with an invalid starting date
      String badEndingDate = "20210921";

      // When the request is made
      mockMvc.perform(get("/transactions?endingDate=%s".formatted(badEndingDate)))
              // Then the response should be a 400
              .andExpect(status().isBadRequest());
      // And the service should not be called
      verify(service, never()).getAllBetween(any(), any());
    }

    @Test
    void callsServiceMethodWithStartingDateFromParameter() throws Exception {
      // Given a correctly formatted startingDate
      String startingDate = "2021-09-21";

      // When the request is made
      mockMvc.perform(get("/transactions?startingDate=%s".formatted(startingDate)));

      // Then the service should be called with the correct parameters
      verify(service, times(1)).getAllBetween(LocalDate.of(2021, 9, 21), defaultEndingDate);
    }

    @Test
    void callsServiceMethodWithEndingDateFromParameter() throws Exception {
      // Given a correctly formatted endingDate
      String endingDate = "2021-09-21";

      // When the request is made
      mockMvc.perform(get("/transactions?endingDate=%s".formatted(endingDate)));

      // Then the service should be called with the correct parameters
      verify(service, times(1)).getAllBetween(defaultStartingDate, LocalDate.of(2021, 9, 21));
    }

    @Test
    void callsMapperWithCorrectDomainObjectsInCorrectOrder() throws Exception {
      // Given two transactions returned from the service
      Transaction transaction1 = createDomain().id(1L).date(LocalDate.now()).build();
      Transaction transaction2 = createDomain().id(2L).date(LocalDate.now().minusDays(1)).build();
      when(service.getAllBetween(defaultStartingDate, defaultEndingDate))
              .thenReturn(List.of(transaction1, transaction2));

      // When the request is made
      mockMvc.perform(get("/transactions"));

      // Then the mapper should be called two times
      verify(mapper, times(2)).mapToResponse(domainCaptor.capture());
      // And the mapper should be called with the correct domain objects
      assert domainCaptor.getAllValues().contains(transaction1);
      assert domainCaptor.getAllValues().contains(transaction2);
      // In the correct order
      assert domainCaptor.getAllValues().get(0).equals(transaction1);
      assert domainCaptor.getAllValues().get(1).equals(transaction2);
    }
  }

  @Nested
  class GetTransactionById {
    @Test
    void transactionIdParameterMustNotBeANegativeNumber() throws Exception {
      // Given a request with a negative transaction id
      // When the request is made
      mockMvc.perform(get("/transactions/-1"))
              // Then the response should be a 400
              .andExpect(status().isBadRequest());
      // And the service should not be called
      verify(service, never()).getById(any());
    }

    @Test
    void returnsCorrectLocationAndLastModifiedHeaders() throws Exception {
      // Given a response dto returned from the mapper
      TransactionResponse responseDto = new TransactionResponse(
              id,
              LocalDate.now(),
              "Merchant",
              2500,
              id,
              "Description",
              lastModifiedAt);
      when(mapper.mapToResponse(any())).thenReturn(responseDto);

      // When the request is made
      mockMvc.perform(get("/transactions/%d".formatted(id)))
              // Then the response should be a 200
              .andExpect(status().isOk())
              // And the response should contain the correct Location header
              .andExpect(header().string("Location", "/transactions/%d".formatted(id)))
              // And the response should contain the correct Last-Modified header
              .andExpect(header().string("Last-Modified", rfc1123Formatter.format(lastModifiedAt)));
    }
  }

  @Nested
  class CreateTransaction {
    @Test
    void returnsCorrectStatusAndHeaders() throws Exception {
      // Given a response dto returned from the mapper
      TransactionResponse responseDto = new TransactionResponse(
              id,
              LocalDate.now(),
              "Merchant",
              2500,
              id,
              "Description",
              lastModifiedAt);
      when(mapper.mapToResponse(any())).thenReturn(responseDto);

      // When the request is made
      mockMvc.perform(post("/transactions")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content("""
                              {
                                "date": "%s",
                                "merchant": "Merchant",
                                "amount": 2500,
                                "description": "Description"
                              }
                              """.formatted(LocalDate.now())))
              // Then the response should be a 201
              .andExpect(status().isCreated())
              // And the response should contain the correct Location header
              .andExpect(header().string("Location", "/transactions/%d".formatted(id)))
              // And the response should contain the correct Last-Modified header
              .andExpect(header().string("Last-Modified", rfc1123Formatter.format(lastModifiedAt)));
    }
  }

  @Nested
  class UpdateTransaction {
    @Test
    void returns400WhenIfUnmodifiedSinceHeaderIsNotPresent() throws Exception {
      // Given a request without an If-Unmodified-Since header
      // When the request is made
      mockMvc.perform(put("/transactions/%d".formatted(id))
                      .contentType(MediaType.APPLICATION_JSON)
                      .content("""
                              {
                                "date": "%s",
                                "merchant": "Merchant",
                                "amount": 2500,
                                "description": "Description"
                              }
                              """.formatted(LocalDate.now())))
              // Then the response should be a 400
              .andExpect(status().isBadRequest());
      // And the service should not be called
      verify(service, never()).update(any(), any(), any());
    }

    @Test
    void returns400WhenTransactionIdParameterIsNegative() throws Exception {
      // Given a response dto returned from the mapper
      TransactionResponse responseDto = new TransactionResponse(
              id,
              LocalDate.now(),
              "Merchant",
              2500,
              id,
              "Description",
              lastModifiedAt);
      when(mapper.mapToResponse(any())).thenReturn(responseDto);
      // And a negative transaction ID

      // When the request is made
      mockMvc.perform(put("/transactions/-1")
                      .contentType(MediaType.APPLICATION_JSON)
                      .header("If-Unmodified-Since", rfc1123Formatter.format(lastModifiedAt))
                      .content("""
                              {
                                "date": "%s",
                                "merchant": "Merchant",
                                "amount": 2500,
                                "description": "Description"
                              }
                              """.formatted(LocalDate.now())))
              // Then the response should be a 400
              .andExpect(status().isBadRequest());
      // And the service should not be called
      verify(service, never()).update(any(), any(), any());
    }

    @Test
    void returnsCorrectStatusAndHeaders() throws Exception {
      // Given a response dto returned from the mapper
      TransactionResponse responseDto = new TransactionResponse(
              id,
              LocalDate.now(),
              "Merchant",
              2500,
              id,
              "Description",
              lastModifiedAt);
      when(mapper.mapToResponse(any())).thenReturn(responseDto);

      // When the request is made
      mockMvc.perform(put("/transactions/1")
                      .contentType(MediaType.APPLICATION_JSON)
                      .header("If-Unmodified-Since", rfc1123Formatter.format(lastModifiedAt))
                      .content("""
                              {
                                "date": "%s",
                                "merchant": "Merchant",
                                "amount": 2500,
                                "description": "Description"
                              }
                              """.formatted(LocalDate.now())))
              // Then the response should be a 201
              .andExpect(status().isOk())
              // And the response should contain the correct Location header
              .andExpect(header().string("Location", "/transactions/%d".formatted(id)))
              // And the response should contain the correct Last-Modified header
              .andExpect(header().string("Last-Modified", rfc1123Formatter.format(lastModifiedAt)));
    }
  }

  @Nested
  class DeleteTransaction {
    @Test
    void returns400WhenTransactionIdParameterIsNegative() throws Exception {
      // Given a negative transaction ID
      // When the request is made
      mockMvc.perform(delete("/transactions/-1"))
              // Then the response should be a 400
              .andExpect(status().isBadRequest());
      // And the service should not be called
      verify(service, never()).delete(any());
    }

    @Test
    void returns204WhenTransactionIdParameterIsValid() throws Exception {
      // Given a negative transaction ID
      // When the request is made
      mockMvc.perform(delete("/transactions/1"))
              // Then the response should be a 204
              .andExpect(status().isNoContent());
      // And the service should be called one time
      verify(service, times(1)).delete(id);
    }
  }

  private Transaction.TransactionBuilder createDomain() {
    return Transaction.builder(LocalDate.now(), "Merchant", 2500)
            .id(1L)
            .description("Description")
            .lineItemId(1L)
            .lastModifiedAt(lastModifiedAt);
  }
}
