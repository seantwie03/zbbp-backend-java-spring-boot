package me.seantwiehaus.zbbp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import me.seantwiehaus.zbbp.domain.Transaction;
import me.seantwiehaus.zbbp.dto.response.TransactionResponse;
import me.seantwiehaus.zbbp.mapper.TransactionMapper;
import me.seantwiehaus.zbbp.service.TransactionService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.parallel.ExecutionMode.SAME_THREAD;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

  private final Long id = 1L;
  private final Instant lastModifiedAt = Instant.parse("2023-02-04T23:31:04.206157Z");
  // The ResponseEntity lastModified(Instant) always has two digits for the day-of-month. If the day-of-month is
  // less than 10, it will add a leading zero. This is correct according to the MDN docs:
  // https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Last-Modified.
  // The DateTimeFormatter.RFC_1123_DATE_TIME does not add the leading zero, so I have to specify the pattern myself.
  // https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html#RFC_1123_DATE_TIME
  private final DateTimeFormatter httpHeaderDateTimeForamtter =
          DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss O").withZone(ZoneOffset.UTC);
  private final ObjectMapper objectMapper = new ObjectMapper()
          // With the JavaTimeModule registered to handle the budgetDate
          .registerModule(new JavaTimeModule()).configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

  @Nested
  class GetAllTransactionsBetween {
    @Captor
    ArgumentCaptor<Transaction> domainCaptor = ArgumentCaptor.forClass(Transaction.class);

    private final LocalDate defaultStartingDate = LocalDate.now().withDayOfMonth(1);
    private final LocalDate defaultEndingDate = LocalDate.now();
    private final TransactionResponse response1 = new TransactionResponse(
            1L,
            LocalDate.of(2023, 2, 4),
            "Merchant 1",
            2500,
            1L,
            "Description 1",
            lastModifiedAt);
    private final TransactionResponse response2 = new TransactionResponse(
            2L,
            LocalDate.of(2023, 2, 3),
            "Merchant 2",
            2400,
            2L,
            "Description 2",
            lastModifiedAt);

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
      String badStartingDate = "20230204";

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
      String badEndingDate = "20230204";

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
      String startingDate = "2023-02-04";

      // When the request is made
      mockMvc.perform(get("/transactions?startingDate=%s".formatted(startingDate)));

      // Then the service should be called with the correct parameters
      verify(service, times(1)).getAllBetween(LocalDate.of(2023, 2, 4), defaultEndingDate);
    }

    @Test
    void callsServiceMethodWithEndingDateFromParameter() throws Exception {
      // Given a correctly formatted endingDate
      String endingDate = "2023-02-04";

      // When the request is made
      mockMvc.perform(get("/transactions?endingDate=%s".formatted(endingDate)));

      // Then the service should be called with the correct parameters
      verify(service, times(1)).getAllBetween(defaultStartingDate, LocalDate.of(2023, 2, 4));
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
      assertTrue(domainCaptor.getAllValues().contains(transaction1));
      assertTrue(domainCaptor.getAllValues().contains(transaction2));
      // In the correct order
      assertEquals(domainCaptor.getAllValues().get(0), transaction1);
      assertEquals(domainCaptor.getAllValues().get(1), transaction2);
    }

    // The next three tests are redundant. They all cover the deserialization of the response body.
    // Each one has some pros and cons. I am keeping all three because over time I would like to see if one
    // stands out by catching more bugs with the fewest false positives.
    @Test
    void returnsCorrectBodyJsonPath() throws Exception {
      // Given two transactions returned from the service
      Transaction transaction1 = createDomain().id(1L).date(LocalDate.of(2023, 2, 4)).build();
      Transaction transaction2 = createDomain().id(2L).date(LocalDate.of(2023, 2, 3)).build();
      when(service.getAllBetween(defaultStartingDate, defaultEndingDate))
              .thenReturn(List.of(transaction1, transaction2));
      // And two TransactionResponses returned from the mapper (declared at class-level)
      when(mapper.mapToResponse(any())).thenReturn(response1, response2);

      // When the request is made
      mockMvc.perform(get("/transactions"))
              // Then the response should be a 200
              .andExpect(status().isOk())
              // And the response body should contain the correct json in the correct order
              .andExpect(jsonPath("$[0].id").value(1L))
              .andExpect(jsonPath("$[0].date").value(LocalDate.of(2023, 2, 4).toString()))
              .andExpect(jsonPath("$[0].merchant").value("Merchant 1"))
              .andExpect(jsonPath("$[0].amount").value(25.00)) // converted to dollars
              .andExpect(jsonPath("$[0].lineItemId").value(1L))
              .andExpect(jsonPath("$[0].description").value("Description 1"))
              .andExpect(jsonPath("$[0].lastModifiedAt").value(lastModifiedAt.toString()))
              .andExpect(jsonPath("$[1].id").value(2L))
              .andExpect(jsonPath("$[1].date").value(LocalDate.of(2023, 2, 3).toString()))
              .andExpect(jsonPath("$[1].merchant").value("Merchant 2"))
              .andExpect(jsonPath("$[1].amount").value(24.00)) // converted to dollars
              .andExpect(jsonPath("$[1].lineItemId").value(2L))
              .andExpect(jsonPath("$[1].description").value("Description 2"))
              .andExpect(jsonPath("$[1].lastModifiedAt").value(lastModifiedAt.toString()));
    }

    @Test
    void returnsCorrectBodyObjectMapper() throws Exception {
      // Given two transactions returned from the service
      Transaction transaction1 = createDomain().id(1L).date(LocalDate.now()).build();
      Transaction transaction2 = createDomain().id(2L).date(LocalDate.now().minusDays(1)).build();
      when(service.getAllBetween(defaultStartingDate, defaultEndingDate))
              .thenReturn(List.of(transaction1, transaction2));
      // And two TransactionResponses returned from the mapper (declared at class-level)
      when(mapper.mapToResponse(any())).thenReturn(response1, response2);

      // When the request is made
      mockMvc.perform(get("/transactions"))
              // Then the response should be a 200
              .andExpect(status().isOk())
              // And the response body should match the list of responses deserialized by ObjectMapper
              .andExpect(content().json(objectMapper.writeValueAsString(List.of(response1, response2))));
    }

    @Test
    void returnsCorrectBodyStringComparison() throws Exception {
      // Given two transactions returned from the service
      Transaction transaction1 = createDomain().id(1L).date(LocalDate.of(2023, 2, 4)).build();
      Transaction transaction2 = createDomain().id(2L).date(LocalDate.of(2023, 2, 3)).build();
      when(service.getAllBetween(defaultStartingDate, defaultEndingDate))
              .thenReturn(List.of(transaction1, transaction2));
      // And two TransactionResponses returned from the mapper (declared at class-level)
      when(mapper.mapToResponse(any())).thenReturn(response1, response2);

      // When the request is made
      String jsonBody = mockMvc.perform(get("/transactions"))
              // Then the response should be a 200
              .andExpect(status().isOk())
              .andReturn()
              .getResponse()
              .getContentAsString();
      // And the response body should contain the correct json
      assertEquals("[{\"id\":1,\"date\":\"2023-02-04\",\"merchant\":\"Merchant 1\",\"amount\":25.00," +
              "\"lineItemId\":1,\"description\":\"Description 1\"," +
              "\"lastModifiedAt\":\"2023-02-04T23:31:04.206157Z\"},{\"id\":2,\"date\":\"2023-02-03\"," +
              "\"merchant\":\"Merchant 2\",\"amount\":24.00,\"lineItemId\":2,\"description\":\"Description 2\"," +
              "\"lastModifiedAt\":\"2023-02-04T23:31:04.206157Z\"}]", jsonBody);
    }
  }

  @Nested
  class GetTransactionById {
    TransactionResponse responseDto = new TransactionResponse(
            id,
            LocalDate.of(2023, 2, 4),
            "Merchant",
            2500,
            id,
            "Description",
            lastModifiedAt);

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
    void returnsCorrectHeaders() throws Exception {
      // Given a response dto returned from the mapper (declared at class-level)
      when(mapper.mapToResponse(any())).thenReturn(responseDto);

      // When the request is made
      mockMvc.perform(get("/transactions/%d".formatted(id)))
              // Then the response should be a 200
              .andExpect(status().isOk())
              // And the response should contain the correct Location header
              .andExpect(header().string("Location", "/transactions/%d".formatted(id)))
              // And the response should contain the correct Last-Modified header
              .andExpect(header().string("Last-Modified", httpHeaderDateTimeForamtter.format(lastModifiedAt)));
    }

    // The next three tests are redundant. They all cover the deserialization of the response body.
    // Each one has some pros and cons. I am keeping all three because over time I would like to see if one
    // stands out by catching more bugs with the fewest false positives.
    @Test
    void returnsCorrectBodyJsonPath() throws Exception {
      // Given a TransactionResponse returned from the mapper (declared at class-level)
      when(mapper.mapToResponse(any())).thenReturn(responseDto);

      // When the request is made
      mockMvc.perform(get("/transactions/%d".formatted(id)))
              // Then the response should be a 200
              .andExpect(status().isOk())
              // And the response body should contain the correct json
              .andExpect(jsonPath("$.id").value(id))
              .andExpect(jsonPath("$.date").value(LocalDate.of(2023, 2, 4).toString()))
              .andExpect(jsonPath("$.merchant").value("Merchant"))
              .andExpect(jsonPath("$.amount").value(25.00)) // converted to dollars
              .andExpect(jsonPath("$.lineItemId").value(id))
              .andExpect(jsonPath("$.description").value("Description"))
              .andExpect(jsonPath("$.lastModifiedAt").value(lastModifiedAt.toString()));
    }

    @Test
    void returnsCorrectBodyObjectMapper() throws Exception {
      // Given a TransactionResponse returned from the mapper (declared at class-level)
      when(mapper.mapToResponse(any())).thenReturn(responseDto);

      // When the request is made
      mockMvc.perform(get("/transactions/%d".formatted(id)))
              // Then the response should be a 200
              .andExpect(status().isOk())
              // And the response should match the responseDto deserialized by ObjectMapper
              .andExpect(content().string(objectMapper.writeValueAsString(responseDto)));
    }

    @Test
    void returnsCorrectBodyStringComparison() throws Exception {
      // Given a TransactionResponse returned from the mapper (declared at class-level)
      when(mapper.mapToResponse(any())).thenReturn(responseDto);

      // When the request is made
      String jsonBody = mockMvc.perform(get("/transactions/%d".formatted(id)))
              // Then the response should be a 200
              .andExpect(status().isOk())
              .andReturn()
              .getResponse()
              .getContentAsString();
      // And the response body should contain the correct json
      assertEquals("{\"id\":1,\"date\":\"2023-02-04\",\"merchant\":\"Merchant\",\"amount\":25.00," +
              "\"lineItemId\":1,\"description\":\"Description\"," +
              "\"lastModifiedAt\":\"2023-02-04T23:31:04.206157Z\"}", jsonBody);
    }
  }

  @Nested
  class CreateTransaction {
    private final TransactionResponse responseDto = new TransactionResponse(
            id,
            LocalDate.of(2023, 2, 4),
            "Merchant",
            2500,
            id,
            "Description",
            lastModifiedAt);

    private final String validContent = """
            {
              "date": "2023-02-04",
              "merchant": "Merchant",
              "amount": 25.00,
              "description": "Description"
            }
            """;

    // Testing the DateTimeFormat and JsonFormat here. These annotation deal with deserialization, not validation. So
    // it is appropriate to test them here.
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = { "01-02-2023", "1, 2, 2023", "01/02/2023", "2023/01/02", "2023-1-2" })
    void returnsBadRequestWhenInvalidDateString(String date) throws Exception {
      String requestContent = """
                 {
                 "date": "%s",
                 "merchant": "Merchant",
                 "amount": 2500,
                 "description": "Description"
               }
              """.formatted(date);

      // When the request is made
      mockMvc.perform(post("/transactions")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(requestContent))
              // Then the response should be a 400
              .andExpect(status().isBadRequest());
    }

    // One test to ensure the request object is validated.
    // The validation tests are in another file. If I were to check each validation annotation at this level, I would
    // have to duplicate the checks for each endpoint that accepts a Request object.
    // More Info in 'Verify Field Validation' section of https://www.arhohuttunen.com/spring-boot-webmvctest/
    @Test
    void returnsBadRequestWhenNotValid() throws Exception {
      // Given a request with invalid content
      String invalidContent = """
              {
                "date": null,
                "merchant": "Merchant",
                "amount": 25.00,
                "description": "Description"
              }
              """;

      // When the request is made
      mockMvc.perform(post("/transactions")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(invalidContent))
              // Then the response should be a 400
              .andExpect(status().isBadRequest());
    }

    @Test
    void returnsCorrectStatusAndHeaders() throws Exception {
      // Given a response dto returned from the mapper (declared at class-level)
      when(mapper.mapToResponse(any())).thenReturn(responseDto);

      // When the request is made
      mockMvc.perform(post("/transactions")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(validContent))
              // Then the response should be a 201
              .andExpect(status().isCreated())
              // And the response should contain the correct Location header
              .andExpect(header().string("Location", "/transactions/%d".formatted(id)))
              // And the response should contain the correct Last-Modified header
              .andExpect(header().string("Last-Modified", httpHeaderDateTimeForamtter.format(lastModifiedAt)));
    }

    // The next three tests are redundant. They all cover the deserialization of the response body.
    // Each one has some pros and cons. I am keeping all three because over time I would like to see if one
    // stands out by catching more bugs with the fewest false positives.
    @Test
    void returnsCorrectBodyJsonPath() throws Exception {
      // Given a TransactionResponse returned from the mapper (declared at class-level)
      when(mapper.mapToResponse(any())).thenReturn(responseDto);

      // When the request is made
      mockMvc.perform(post("/transactions")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(validContent))
              // Then the response should be a 201
              .andExpect(status().isCreated())
              // And the response body should contain the correct json
              .andExpect(jsonPath("$.id").value(id))
              .andExpect(jsonPath("$.date").value(LocalDate.of(2023, 2, 4).toString()))
              .andExpect(jsonPath("$.merchant").value("Merchant"))
              .andExpect(jsonPath("$.amount").value(25.00)) // converted to dollars
              .andExpect(jsonPath("$.lineItemId").value(id))
              .andExpect(jsonPath("$.description").value("Description"))
              .andExpect(jsonPath("$.lastModifiedAt").value(lastModifiedAt.toString()));
    }

    @Test
    void returnsCorrectBodyObjectMapper() throws Exception {
      // Given a TransactionResponse returned from the mapper (declared at class-level)
      when(mapper.mapToResponse(any())).thenReturn(responseDto);

      // When the request is made
      mockMvc.perform(post("/transactions")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(validContent))
              // Then the response should be a 201
              .andExpect(status().isCreated())
              // And the response should match the responseDto deserialized by ObjectMapper
              .andExpect(content().string(objectMapper.writeValueAsString(responseDto)));
    }

    @Test
    void returnsCorrectBodyStringComparison() throws Exception {
      // Given a TransactionResponse returned from the mapper (declared at class-level)
      when(mapper.mapToResponse(any())).thenReturn(responseDto);

      // When the request is made
      String jsonBody = mockMvc.perform(post("/transactions")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(validContent))
              // Then the response should be a 201
              .andExpect(status().isCreated())
              .andReturn()
              .getResponse()
              .getContentAsString();
      // And the response body should contain the correct json
      assertEquals("{\"id\":1,\"date\":\"2023-02-04\",\"merchant\":\"Merchant\",\"amount\":25.00," +
              "\"lineItemId\":1,\"description\":\"Description\"," +
              "\"lastModifiedAt\":\"2023-02-04T23:31:04.206157Z\"}", jsonBody);
    }
  }

  @Nested
  class UpdateTransaction {
    private final TransactionResponse responseDto = new TransactionResponse(
            id,
            LocalDate.of(2023, 2, 4),
            "Merchant",
            25_00,
            id,
            "Description",
            lastModifiedAt);

    private final String validContent = """
            {
              "date": "2023-02-04",
              "merchant": "Merchant",
              "amount": 25.00,
              "description": "Description"
            }
            """;

    @Test
    void returns400WhenIfUnmodifiedSinceHeaderIsNotPresent() throws Exception {
      // Given a request without an If-Unmodified-Since header
      // When the request is made
      mockMvc.perform(put("/transactions/%d".formatted(id))
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(validContent))
              // Then the response should be a 400
              .andExpect(status().isBadRequest());
      // And the service should not be called
      verify(service, never()).update(any(), any(), any());
    }

    @Test
    void returns400WhenTransactionIdParameterIsNegative() throws Exception {
      // Given a request with a negative id parameter
      // When the request is made
      mockMvc.perform(put("/transactions/-1")
                      .contentType(MediaType.APPLICATION_JSON)
                      .header("If-Unmodified-Since", httpHeaderDateTimeForamtter.format(lastModifiedAt))
                      .content(validContent))
              // Then the response should be a 400
              .andExpect(status().isBadRequest());
      // And the service should not be called
      verify(service, never()).update(any(), any(), any());
    }

    // One test to ensure the request object is validated.
    // The validation tests are in another file. If I were to check each validation annotation at this level, I would
    // have to duplicate the checks for each endpoint that accepts a Request object.
    // More Info in 'Verify Field Validation' section of https://www.arhohuttunen.com/spring-boot-webmvctest/
    @Test
    void returnsBadRequestWhenNotValid() throws Exception {
      // Given a request with invalid content
      String invalidContent = """
              {
                "date": null,
                "merchant": "Merchant",
                "amount": 25.00,
                "description": "Description"
              }
              """;

      // When the request is made
      mockMvc.perform(post("/transactions")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(invalidContent))
              // Then the response should be a 400
              .andExpect(status().isBadRequest());
    }

    @Test
    void returnsCorrectStatusAndHeaders() throws Exception {
      // Given a response dto returned from the mapper (declared at class-level)
      when(mapper.mapToResponse(any())).thenReturn(responseDto);

      // When the request is made
      mockMvc.perform(put("/transactions/%d".formatted(id))
                      .contentType(MediaType.APPLICATION_JSON)
                      .header("If-Unmodified-Since", httpHeaderDateTimeForamtter.format(lastModifiedAt))
                      .content(validContent))
              // Then the response should be a 201
              .andExpect(status().isOk())
              // And the response should contain the correct Location header
              .andExpect(header().string("Location", "/transactions/%d".formatted(id)))
              // And the response should contain the correct Last-Modified header
              .andExpect(header().string("Last-Modified", httpHeaderDateTimeForamtter.format(lastModifiedAt)));
    }

    // The next three tests are redundant. They all cover the deserialization of the response body.
    // Each one has some pros and cons. I am keeping all three because over time I would like to see if one
    // stands out by catching more bugs with the fewest false positives.
    @Test
    void returnsCorrectBodyJsonPath() throws Exception {
      // Given a TransactionResponse returned from the mapper (declared at class-level)
      when(mapper.mapToResponse(any())).thenReturn(responseDto);

      // When the request is made
      mockMvc.perform(put("/transactions/%d".formatted(id))
                      .contentType(MediaType.APPLICATION_JSON)
                      .header("If-Unmodified-Since", httpHeaderDateTimeForamtter.format(lastModifiedAt))
                      .content(validContent))
              // Then the response should be a 200
              .andExpect(status().isOk())
              // And the response body should contain the correct json
              .andExpect(jsonPath("$.id").value(id))
              .andExpect(jsonPath("$.date").value(LocalDate.of(2023, 2, 4).toString()))
              .andExpect(jsonPath("$.merchant").value("Merchant"))
              .andExpect(jsonPath("$.amount").value(25.00)) // converted to dollars
              .andExpect(jsonPath("$.lineItemId").value(id))
              .andExpect(jsonPath("$.description").value("Description"))
              .andExpect(jsonPath("$.lastModifiedAt").value(lastModifiedAt.toString()));
    }

    @Test
    void returnsCorrectBodyObjectMapper() throws Exception {
      // Given a TransactionResponse returned from the mapper (declared at class-level)
      when(mapper.mapToResponse(any())).thenReturn(responseDto);

      // When the request is made
      mockMvc.perform(put("/transactions/%d".formatted(id))
                      .contentType(MediaType.APPLICATION_JSON)
                      .header("If-Unmodified-Since", httpHeaderDateTimeForamtter.format(lastModifiedAt))
                      .content(validContent))
              // Then the response should be a 200
              .andExpect(status().isOk())
              // And the response should match the responseDto deserialized by ObjectMapper
              .andExpect(content().string(objectMapper.writeValueAsString(responseDto)));
    }

    @Test
    void returnsCorrectBodyStringComparison() throws Exception {
      // Given a TransactionResponse returned from the mapper (declared at class-level)
      when(mapper.mapToResponse(any())).thenReturn(responseDto);

      // When the request is made
      String jsonBody = mockMvc.perform(put("/transactions/%d".formatted(id))
                      .contentType(MediaType.APPLICATION_JSON)
                      .header("If-Unmodified-Since", httpHeaderDateTimeForamtter.format(lastModifiedAt))
                      .content(validContent))
              // Then the response should be a 200
              .andExpect(status().isOk())
              .andReturn()
              .getResponse()
              .getContentAsString();
      // And the response body should contain the correct json
      assertEquals("{\"id\":1,\"date\":\"2023-02-04\",\"merchant\":\"Merchant\",\"amount\":25.00," +
              "\"lineItemId\":1,\"description\":\"Description\"," +
              "\"lastModifiedAt\":\"2023-02-04T23:31:04.206157Z\"}", jsonBody);
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
    return Transaction.builder(LocalDate.now(), "Merchant", 25_00)
            .id(1L)
            .description("Description")
            .lineItemId(1L)
            .lastModifiedAt(lastModifiedAt);
  }
}
