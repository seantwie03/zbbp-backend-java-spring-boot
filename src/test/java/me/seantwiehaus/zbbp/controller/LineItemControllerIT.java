package me.seantwiehaus.zbbp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import me.seantwiehaus.zbbp.domain.Category;
import me.seantwiehaus.zbbp.domain.LineItem;
import me.seantwiehaus.zbbp.dto.response.LineItemResponse;
import me.seantwiehaus.zbbp.mapper.LineItemMapper;
import me.seantwiehaus.zbbp.service.LineItemService;
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
import java.time.YearMonth;
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
@WebMvcTest(LineItemController.class)
public class LineItemControllerIT {
  @MockBean
  LineItemService service;
  @MockBean
  LineItemMapper mapper;

  @Autowired
  MockMvc mockMvc;

  private final Long id = 1L;
  private final Instant lastModifiedAt = Instant.parse("2022-09-21T23:31:04.206157Z");
  // The ResponseEntity lastModified(Instant) always has two digits for the day-of-month. If the day-of-month is
  // less than 10, it will add a leading zero. This is correct according to the MDN docs:
  // https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Last-Modified.
  // The DateTimeFormatter.RFC_1123_DATE_TIME does not add the leading zero, so I have to specify the pattern myself.
  // https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html#RFC_1123_DATE_TIME
  private final DateTimeFormatter lastModifiedFormatter =
          DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss O").withZone(ZoneOffset.UTC);
  private final ObjectMapper objectMapper = new ObjectMapper()
          // With the JavaTimeModule registered to handle the budgetDate
          .registerModule(new JavaTimeModule()).configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

  @Nested
  class GetAllLineItemsBetween {
    @Captor
    ArgumentCaptor<LineItem> domainCaptor = ArgumentCaptor.forClass(LineItem.class);

    private final YearMonth defaultStartingDate = YearMonth.now();
    private final YearMonth defaultEndingDate = YearMonth.now();
    private final LineItemResponse response1 = new LineItemResponse(
            1L,
            YearMonth.of(2021, 9),
            "Name 1",
            120000,
            Category.FOOD,
            "description 1",
            lastModifiedAt,
            0,
            0.0,
            120000,
            List.of());
    private final LineItemResponse response2 = new LineItemResponse(
            2L,
            YearMonth.of(2021, 8),
            "Name 2",
            110000,
            Category.FOOD,
            "description 2",
            lastModifiedAt,
            0,
            0.0,
            110000,
            List.of());

    @Test
    void callsServiceWithDefaultStartingAndEndingDateWhenNoneProvided() throws Exception {
      // Given a request with no starting or ending date
      // When the request is made
      mockMvc.perform(get("/line-items"));

      // Then the service should be called one time with the default starting and ending dates
      verify(service, times(1)).getAllBetween(defaultStartingDate, defaultEndingDate);
    }

    @Test
    void returns400WhenStartingDateIsNotCorrectlyFormatted() throws Exception {
      // Given a request with an invalid starting date
      String badStartingDate = "202109";

      // When the request is made
      mockMvc.perform(get("/line-items?startingBudgetDate=%s".formatted(badStartingDate)))
              // Then the response should be a 400
              .andExpect(status().isBadRequest());
      // And the service should not be called
      verify(service, never()).getAllBetween(any(), any());
    }

    @Test
    void returns400WhenEndingDateIsNotCorrectlyFormatted() throws Exception {
      // Given a request with an invalid starting date
      String badEndingDate = "202109";

      // When the request is made
      mockMvc.perform(get("/line-items?endingBudgetDate=%s".formatted(badEndingDate)))
              // Then the response should be a 400
              .andExpect(status().isBadRequest());
      // And the service should not be called
      verify(service, never()).getAllBetween(any(), any());
    }

    @Test
    void callsServiceMethodWithStartingDateFromParameter() throws Exception {
      // Given a correctly formatted startingDate
      String startingDate = "2021-09";

      // When the request is made
      mockMvc.perform(get("/line-items").param("startingBudgetDate", startingDate));

      // Then the service should be called with the correct parameters
      verify(service, times(1)).getAllBetween(YearMonth.of(2021, 9), defaultEndingDate);
    }

    @Test
    void callsServiceMethodWithEndingDateFromParameter() throws Exception {
      // Given a correctly formatted endingDate
      String endingDate = "2021-09";

      // When the request is made
      mockMvc.perform(get("/line-items").param("endingBudgetDate", endingDate));

      // Then the service should be called with the correct parameters
      verify(service, times(1)).getAllBetween(defaultStartingDate, YearMonth.of(2021, 9));
    }

    @Test
    void callsMapperWithCorrectDomainObjectsInCorrectOrder() throws Exception {
      // Given two LineItems returned from the service
      LineItem lineItem1 = createDomain().id(1L).budgetDate(YearMonth.of(2021, 9)).build();
      LineItem lineItem2 = createDomain().id(2L).budgetDate(YearMonth.of(2021, 8)).build();
      when(service.getAllBetween(defaultStartingDate, defaultEndingDate))
              .thenReturn(List.of(lineItem1, lineItem2));

      // When the request is made
      mockMvc.perform(get("/line-items"));

      // Then the mapper should be called two times
      verify(mapper, times(2)).mapToResponse(domainCaptor.capture());
      // And the mapper should be called with the correct domain objects
      assertTrue(domainCaptor.getAllValues().contains(lineItem1));
      assertTrue(domainCaptor.getAllValues().contains(lineItem2));
      // In the correct order
      assertEquals(domainCaptor.getAllValues().get(0), lineItem1);
      assertEquals(domainCaptor.getAllValues().get(1), lineItem2);
    }

    // The next three tests are redundant. They all cover the deserialization of the response body.
    // Each one has some pros and cons. I am keeping all three because over time I would like to see if one
    // stands out by catching more bugs with the fewest false positives.
    @Test
    void returnsCorrectBodyJsonPath() throws Exception {
      // Given two LineItems returned from the service
      LineItem lineItem1 = createDomain().id(1L).budgetDate(YearMonth.of(2021, 9)).build();
      LineItem lineItem2 = createDomain().id(2L).budgetDate(YearMonth.of(2021, 8)).build();
      when(service.getAllBetween(defaultStartingDate, defaultEndingDate))
              .thenReturn(List.of(lineItem1, lineItem2));
      // And two LineItemResponses returned from the mapper (declared at class-level)
      when(mapper.mapToResponse(any())).thenReturn(response1, response2);

      // When the request is made
      mockMvc.perform(get("/line-items"))
              // Then the response should be a 200
              .andExpect(status().isOk())
              // And the response body should contain the correct data in the correct order
              .andExpect(jsonPath("$[0].id").value(1L))
              .andExpect(jsonPath("$[0].budgetDate").value("2021-09"))
              .andExpect(jsonPath("$[0].name").value("Name 1"))
              .andExpect(jsonPath("$[0].plannedAmount").value(1200.00)) // converted to dollars
              .andExpect(jsonPath("$[0].category").value("FOOD"))
              .andExpect(jsonPath("$[0].description").value("description 1"))
              .andExpect(jsonPath("$[0].lastModifiedAt").value(lastModifiedAt.toString()))
              .andExpect(jsonPath("$[0].totalTransactions").value(0.00)) // converted to dollars
              .andExpect(jsonPath("$[0].percentageOfPlanned").value(0.0))
              .andExpect(jsonPath("$[0].totalRemaining").value(1200.00)) // converted to dollars
              .andExpect(jsonPath("$[0].transactions").isEmpty())
              .andExpect(jsonPath("$[1].id").value(2L))
              .andExpect(jsonPath("$[1].budgetDate").value("2021-08"))
              .andExpect(jsonPath("$[1].name").value("Name 2"))
              .andExpect(jsonPath("$[1].plannedAmount").value(1100.00)) // converted to dollars
              .andExpect(jsonPath("$[1].category").value("FOOD"))
              .andExpect(jsonPath("$[1].description").value("description 2"))
              .andExpect(jsonPath("$[1].lastModifiedAt").value(lastModifiedAt.toString()))
              .andExpect(jsonPath("$[1].totalTransactions").value(0.00)) // converted to dollars
              .andExpect(jsonPath("$[1].percentageOfPlanned").value(0.0))
              .andExpect(jsonPath("$[1].totalRemaining").value(1100.00)) // converted to dollars
              .andExpect(jsonPath("$[1].transactions").isEmpty());
    }

    @Test
    void returnsCorrectBodyObjectMapper() throws Exception {
      // Given two LineItems returned from the service
      LineItem lineItem1 = createDomain().id(1L).budgetDate(YearMonth.of(2021, 9)).build();
      LineItem lineItem2 = createDomain().id(2L).budgetDate(YearMonth.of(2021, 8)).build();
      when(service.getAllBetween(defaultStartingDate, defaultEndingDate))
              .thenReturn(List.of(lineItem1, lineItem2));
      // And two LineItemResponses returned from the mapper (declared at class-level)
      when(mapper.mapToResponse(any())).thenReturn(response1, response2);

      // When the request is made
      mockMvc.perform(get("/line-items"))
              // Then the response should be a 200
              .andExpect(status().isOk())
              // And the response body should match the list of responses deserialized by ObjectMapper
              .andExpect(content().string(objectMapper.writeValueAsString(List.of(response1, response2))));
    }

    @Test
    void returnsCorrectBodyStringComparison() throws Exception {
      // Given two LineItems returned from the service
      LineItem lineItem1 = createDomain().id(1L).budgetDate(YearMonth.of(2021, 9)).build();
      LineItem lineItem2 = createDomain().id(2L).budgetDate(YearMonth.of(2021, 8)).build();
      when(service.getAllBetween(defaultStartingDate, defaultEndingDate))
              .thenReturn(List.of(lineItem1, lineItem2));
      // And two LineItemResponses returned from the mapper (declared at class-level)
      when(mapper.mapToResponse(any())).thenReturn(response1, response2);

      // When the request is made
      String jsonBody = mockMvc.perform(get("/line-items"))
              // Then the response should be a 200
              .andExpect(status().isOk())
              .andReturn()
              .getResponse()
              .getContentAsString();
      // And the response body should contain the correct json
      assertEquals("[{\"id\":1,\"budgetDate\":\"2021-09\",\"name\":\"Name 1\",\"plannedAmount\":1200.00," +
              "\"category\":\"FOOD\",\"description\":\"description 1\"," +
              "\"lastModifiedAt\":\"2022-09-21T23:31:04.206157Z\",\"totalTransactions\":0.00," +
              "\"percentageOfPlanned\":0.0,\"totalRemaining\":1200.00,\"transactions\":[]}," +
              "{\"id\":2,\"budgetDate\":\"2021-08\",\"name\":\"Name 2\",\"plannedAmount\":1100.00," +
              "\"category\":\"FOOD\",\"description\":\"description 2\"," +
              "\"lastModifiedAt\":\"2022-09-21T23:31:04.206157Z\",\"totalTransactions\":0.00," +
              "\"percentageOfPlanned\":0.0,\"totalRemaining\":1100.00,\"transactions\":[]}]", jsonBody);
    }
  }

  @Nested
  class GetLineItemById {
    private final LineItemResponse responseDto = new LineItemResponse(
            1L,
            YearMonth.of(2021, 9),
            "Name",
            120000,
            Category.FOOD,
            "description",
            lastModifiedAt,
            0,
            0.0,
            120000,
            List.of());

    @Test
    void lineItemIdParameterMustNotBeANegativeNumber() throws Exception {
      // Given a request with a negative lineItem id
      // When the request is made
      mockMvc.perform(get("/line-items/-1"))
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
      mockMvc.perform(get("/line-items/%d".formatted(id)))
              // Then the response should be a 200
              .andExpect(status().isOk())
              // And the response should contain the correct Location header
              .andExpect(header().string("Location", "/line-items/%d".formatted(id)))
              // And the response should contain the correct Last-Modified header
              .andExpect(header().string("Last-Modified", lastModifiedFormatter.format(lastModifiedAt)));
    }

    // The next three tests are redundant. They all cover the deserialization of the response body.
    // Each one has some pros and cons. I am keeping all three because over time I would like to see if one
    // stands out by catching more bugs with the fewest false positives.
    @Test
    void returnsCorrectBodyJsonPath() throws Exception {
      // Given a response dto returned from the mapper (declared at class-level)
      when(mapper.mapToResponse(any())).thenReturn(responseDto);

      // When the request is made
      mockMvc.perform(get("/line-items/%d".formatted(id)))
              // Then the response should be a 200
              .andExpect(status().isOk())
              // And the response body should contain the correct data
              .andExpect(jsonPath("$.id").value(1L))
              .andExpect(jsonPath("$.budgetDate").value("2021-09"))
              .andExpect(jsonPath("$.name").value("Name"))
              .andExpect(jsonPath("$.plannedAmount").value(1200.00)) // should've been converted to dollars
              .andExpect(jsonPath("$.category").value("FOOD"))
              .andExpect(jsonPath("$.description").value("description"))
              .andExpect(jsonPath("$.lastModifiedAt").value(lastModifiedAt.toString()))
              .andExpect(jsonPath("$.totalTransactions").value(0.00))
              .andExpect(jsonPath("$.percentageOfPlanned").value(0.0))
              .andExpect(jsonPath("$.totalRemaining").value(1200.00)) // should've been converted to dollars
              .andExpect(jsonPath("$.transactions").isEmpty());
    }

    @Test
    void returnsCorrectBodyObjectMapper() throws Exception {
      // Given a response dto returned from the mapper (declared at class-level)
      when(mapper.mapToResponse(any())).thenReturn(responseDto);

      // When the request is made
      mockMvc.perform(get("/line-items/%d".formatted(id)))
              // Then the response should be a 200
              .andExpect(status().isOk())
              // And the response should match the responseDto deserialized by ObjectMapper
              .andExpect(content().string(objectMapper.writeValueAsString(responseDto)));
    }

    @Test
    void returnsCorrectBodyStringComparison() throws Exception {
      // Given a response dto returned from the mapper (declared at class-level)
      when(mapper.mapToResponse(any())).thenReturn(responseDto);

      // When the request is made
      String jsonBody = mockMvc.perform(get("/line-items/%d".formatted(id)))
              // Then the response should be a 200
              .andExpect(status().isOk())
              .andReturn()
              .getResponse()
              .getContentAsString();
      // And the response body should contain the correct json
      assertEquals("{\"id\":1,\"budgetDate\":\"2021-09\",\"name\":\"Name\",\"plannedAmount\":1200.00," +
              "\"category\":\"FOOD\",\"description\":\"description\"," +
              "\"lastModifiedAt\":\"2022-09-21T23:31:04.206157Z\",\"totalTransactions\":0.00," +
              "\"percentageOfPlanned\":0.0,\"totalRemaining\":1200.00,\"transactions\":[]}", jsonBody);
    }
  }

  @Nested
  class CreateLineItem {
    private final LineItemResponse responseDto = new LineItemResponse(
            1L,
            YearMonth.of(2021, 9),
            "Name",
            120000,
            Category.FOOD,
            "description",
            lastModifiedAt,
            0,
            0.0,
            120000,
            List.of());

    // Testing the DateTimeFormat here. These annotation deal with deserialization, not validation. So
    // it is appropriate to test them here.
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = { "01-2023", "1, 2023", "01/2023", "2023/01", "2023-1" })
    void returnsBadRequestWhenInvalidDateString(String date) throws Exception {
      String requestContent = """
              {
                "budgetDate": "%s",
                "name": "Name",
                "plannedAmount": 2500.00,
                "category": "FOOD",
                "description": "description"
              }
              """.formatted(date);
      // When the request is made
      mockMvc.perform(post("/line-items")
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
      // Given a request with a null merchant
      // When the request is made
      mockMvc.perform(post("/line-items")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content("""
                              {
                                "budgetDate": null,
                                "name": "Name",
                                "plannedAmount": 2500.00,
                                "category": "FOOD",
                                "description": "description"
                              }
                              """))
              // Then the response should be a 400
              .andExpect(status().isBadRequest());
    }

    @Test
    void returnsCorrectStatusAndHeaders() throws Exception {
      // Given a response dto returned from the mapper (declared at class-level)
      when(mapper.mapToResponse(any())).thenReturn(responseDto);

      // When the request is made
      mockMvc.perform(post("/line-items")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content("""
                              {
                                "budgetDate": "%s",
                                "name": "Name",
                                "plannedAmount": 2500.00,
                                "category": "FOOD",
                                "description": "description"
                              }
                              """.formatted(YearMonth.of(2021, 9))))
              // Then the response should be a 201
              .andExpect(status().isCreated())
              // And the response should contain the correct Location header
              .andExpect(header().string("Location", "/line-items/%d".formatted(id)))
              // And the response should contain the correct Last-Modified header
              .andExpect(header().string("Last-Modified", lastModifiedFormatter.format(lastModifiedAt)));
    }

    // The next three tests are redundant. They all cover the deserialization of the response body.
    // Each one has some pros and cons. I am keeping all three because over time I would like to see if one
    // stands out by catching more bugs with the fewest false positives.
    @Test
    void returnsCorrectBodyJsonPath() throws Exception {
      // Given a response dto returned from the mapper (declared at class-level)
      when(mapper.mapToResponse(any())).thenReturn(responseDto);

      // When the request is made
      mockMvc.perform(post("/line-items")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content("""
                              {
                                "budgetDate": "2021-09",
                                "name": "Name",
                                "plannedAmount": 2500.00,
                                "category": "FOOD",
                                "description": "description"
                              }
                              """))
              // Then the response should be a 201
              .andExpect(status().isCreated())
              // And the response body should contain the correct data
              .andExpect(jsonPath("$.id").value(1L))
              .andExpect(jsonPath("$.budgetDate").value("2021-09"))
              .andExpect(jsonPath("$.name").value("Name"))
              .andExpect(jsonPath("$.plannedAmount").value(1200.00)) // should've been converted to dollars
              .andExpect(jsonPath("$.category").value("FOOD"))
              .andExpect(jsonPath("$.description").value("description"))
              .andExpect(jsonPath("$.lastModifiedAt").value(lastModifiedAt.toString()))
              .andExpect(jsonPath("$.totalTransactions").value(0.00))
              .andExpect(jsonPath("$.percentageOfPlanned").value(0.0))
              .andExpect(jsonPath("$.totalRemaining").value(1200.00)) // should've been converted to dollars
              .andExpect(jsonPath("$.transactions").isEmpty());
    }

    @Test
    void returnsCorrectBodyObjectMapper() throws Exception {
      // Given a response dto returned from the mapper (declared at class-level)
      when(mapper.mapToResponse(any())).thenReturn(responseDto);

      // When the request is made
      mockMvc.perform(post("/line-items")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content("""
                              {
                                "budgetDate": "2021-09",
                                "name": "Name",
                                "plannedAmount": 2500.00,
                                "category": "FOOD",
                                "description": "description"
                              }
                              """))
              // Then the response should be a 201
              .andExpect(status().isCreated())
              // And the response should match the responseDto deserialized by ObjectMapper
              .andExpect(content().string(objectMapper.writeValueAsString(responseDto)));
    }

    @Test
    void returnsCorrectBodyStringComparison() throws Exception {
      // Given a response dto returned from the mapper (declared at class-level)
      when(mapper.mapToResponse(any())).thenReturn(responseDto);

      // When the request is made
      String jsonBody = mockMvc.perform(post("/line-items")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content("""
                              {
                                "budgetDate": "2021-09",
                                "name": "Name",
                                "plannedAmount": 2500.00,
                                "category": "FOOD",
                                "description": "description"
                              }
                              """))
              // Then the response should be a 201
              .andExpect(status().isCreated())
              .andReturn()
              .getResponse()
              .getContentAsString();
      // And the response body should contain the correct json
      assertEquals("{\"id\":1,\"budgetDate\":\"2021-09\",\"name\":\"Name\",\"plannedAmount\":1200.00," +
              "\"category\":\"FOOD\",\"description\":\"description\"," +
              "\"lastModifiedAt\":\"2022-09-21T23:31:04.206157Z\",\"totalTransactions\":0.00," +
              "\"percentageOfPlanned\":0.0,\"totalRemaining\":1200.00,\"transactions\":[]}", jsonBody);
    }
  }

  @Nested
  class UpdateLineItem {
    private final LineItemResponse responseDto = new LineItemResponse(
            1L,
            YearMonth.of(2021, 9),
            "Name",
            1200_00,
            Category.FOOD,
            "description",
            lastModifiedAt,
            0,
            0.0,
            1200_00,
            List.of());

    @Test
    void returns400WhenIfUnmodifiedSinceHeaderIsNotPresent() throws Exception {
      // Given a request without an If-Unmodified-Since header
      // When the request is made
      mockMvc.perform(put("/line-items/%d".formatted(id))
                      .contentType(MediaType.APPLICATION_JSON)
                      .content("""
                              {
                                "budgetDate": "2021-09",
                                "name": "Name",
                                "plannedAmount": 1200.00,
                                "category": "FOOD",
                                "description": "description"
                              }
                              """))
              // Then the response should be a 400
              .andExpect(status().isBadRequest());
      // And the service should not be called
      verify(service, never()).update(any(), any(), any());
    }

    @Test
    void returns400WhenLineItemIdParameterIsNegative() throws Exception {
      // Given a request with a negative id parameter
      // When the request is made
      mockMvc.perform(put("/line-items/-1")
                      .contentType(MediaType.APPLICATION_JSON)
                      .header("If-Unmodified-Since", lastModifiedFormatter.format(lastModifiedAt))
                      .content("""
                              {
                                "budgetDate": "2021-09",
                                "name": "Name",
                                "plannedAmount": 1200.00,
                                "category": "FOOD",
                                "description": "description"
                              }
                              """))
              // Then the response should be a 400
              .andExpect(status().isBadRequest());
      // And the service should not be called
      verify(service, never()).update(any(), any(), any());
    }

    @Test
    void returnsCorrectStatusAndHeaders() throws Exception {
      // Given a response dto returned from the mapper (declared at class-level)
      when(mapper.mapToResponse(any())).thenReturn(responseDto);

      // When the request is made
      mockMvc.perform(put("/line-items/%d".formatted(id))
                      .contentType(MediaType.APPLICATION_JSON)
                      .header("If-Unmodified-Since", lastModifiedFormatter.format(lastModifiedAt))
                      .content("""
                              {
                                "budgetDate": "2021-09",
                                "name": "Name",
                                "plannedAmount": 1200.00,
                                "category": "FOOD",
                                "description": "description"
                              }
                              """))
              // Then the response should be a 200
              .andExpect(status().isOk())
              // And the response should contain the correct Location header
              .andExpect(header().string("Location", "/line-items/%d".formatted(id)))
              // And the response should contain the correct Last-Modified header
              .andExpect(header().string("Last-Modified", lastModifiedFormatter.format(lastModifiedAt)));
    }

    // The next three tests are redundant. They all cover the deserialization of the response body.
    // Each one has some pros and cons. I am keeping all three because over time I would like to see if one
    // stands out by catching more bugs with the fewest false positives.
    @Test
    void returnsCorrectBodyJsonPath() throws Exception {
      // Given a response dto returned from the mapper (declared at class-level)
      when(mapper.mapToResponse(any())).thenReturn(responseDto);

      // When the request is made
      mockMvc.perform(put("/line-items/%d".formatted(id))
                      .contentType(MediaType.APPLICATION_JSON)
                      .header("If-Unmodified-Since", lastModifiedFormatter.format(lastModifiedAt))
                      .content("""
                              {
                                "budgetDate": "2021-09",
                                "name": "Name",
                                "plannedAmount": 1200.00,
                                "category": "FOOD",
                                "description": "description"
                              }
                              """))
              // Then the response should be a 200
              .andExpect(status().isOk())
              // And the response body should contain the correct data
              .andExpect(jsonPath("$.id").value(1L))
              .andExpect(jsonPath("$.budgetDate").value("2021-09"))
              .andExpect(jsonPath("$.name").value("Name"))
              .andExpect(jsonPath("$.plannedAmount").value(1200.00)) // should've been converted to dollars
              .andExpect(jsonPath("$.category").value("FOOD"))
              .andExpect(jsonPath("$.description").value("description"))
              .andExpect(jsonPath("$.lastModifiedAt").value(lastModifiedAt.toString()))
              .andExpect(jsonPath("$.totalTransactions").value(0.00))
              .andExpect(jsonPath("$.percentageOfPlanned").value(0.0))
              .andExpect(jsonPath("$.totalRemaining").value(1200.00)) // should've been converted to dollars
              .andExpect(jsonPath("$.transactions").isEmpty());
    }

    @Test
    void returnsCorrectBodyObjectMapper() throws Exception {
      // Given a response dto returned from the mapper (declared at class-level)
      when(mapper.mapToResponse(any())).thenReturn(responseDto);

      // When the request is made
      mockMvc.perform(put("/line-items/%d".formatted(id))
                      .contentType(MediaType.APPLICATION_JSON)
                      .header("If-Unmodified-Since", lastModifiedFormatter.format(lastModifiedAt))
                      .content("""
                              {
                                "budgetDate": "2021-09",
                                "name": "Name",
                                "plannedAmount": 2500.00,
                                "category": "FOOD",
                                "description": "description"
                              }
                              """))
              // Then the response should be a 200
              .andExpect(status().isOk())
              // And the response should match the responseDto deserialized by ObjectMapper
              .andExpect(content().string(objectMapper.writeValueAsString(responseDto)));
    }

    @Test
    void returnsCorrectBodyStringComparison() throws Exception {
      // Given a response dto returned from the mapper (declared at class-level)
      when(mapper.mapToResponse(any())).thenReturn(responseDto);

      // When the request is made
      String jsonBody = mockMvc.perform(put("/line-items/%d".formatted(id))
                      .contentType(MediaType.APPLICATION_JSON)
                      .header("If-Unmodified-Since", lastModifiedFormatter.format(lastModifiedAt))
                      .content("""
                              {
                                "budgetDate": "2021-09",
                                "name": "Name",
                                "plannedAmount": 2500.00,
                                "category": "FOOD",
                                "description": "description"
                              }
                              """))
              // Then the response should be a 200
              .andExpect(status().isOk())
              .andReturn()
              .getResponse()
              .getContentAsString();
      // And the response body should contain the correct json
      assertEquals("{\"id\":1,\"budgetDate\":\"2021-09\",\"name\":\"Name\",\"plannedAmount\":1200.00," +
              "\"category\":\"FOOD\",\"description\":\"description\"," +
              "\"lastModifiedAt\":\"2022-09-21T23:31:04.206157Z\",\"totalTransactions\":0.00," +
              "\"percentageOfPlanned\":0.0,\"totalRemaining\":1200.00,\"transactions\":[]}", jsonBody);
    }
  }

  private LineItem.LineItemBuilder createDomain() {
    return LineItem.builder(YearMonth.of(2021, 9), "Name", 140000, Category.FOOD)
            .id(1L)
            .description("Description")
            .lastModifiedAt(lastModifiedAt);
  }
}
