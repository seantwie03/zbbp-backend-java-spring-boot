package me.seantwiehaus.zbbp.controller;

import me.seantwiehaus.zbbp.domain.Budget;
import me.seantwiehaus.zbbp.domain.Category;
import me.seantwiehaus.zbbp.domain.LineItem;
import me.seantwiehaus.zbbp.dto.response.BudgetResponse;
import me.seantwiehaus.zbbp.dto.response.LineItemResponse;
import me.seantwiehaus.zbbp.mapper.BudgetMapper;
import me.seantwiehaus.zbbp.service.BudgetService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.time.YearMonth;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.parallel.ExecutionMode.SAME_THREAD;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// Cannot run in parallel with other tests due to @MockBean
// https://docs.spring.io/spring-framework/docs/current/reference/html/testing.html#testcontext-parallel-test-execution
// https://stackoverflow.com/a/64628499/15513517
@Execution(SAME_THREAD)
@WebMvcTest(BudgetController.class)
public class BudgetControllerIT {
  @MockBean
  BudgetService service;
  @MockBean
  BudgetMapper mapper;

  @Autowired
  MockMvc mockMvc;

  private final Instant lastModifiedAt = Instant.parse("2022-09-21T23:31:04.206157Z");

  @Nested
  class GetFor {
    @Captor
    ArgumentCaptor<Budget> domainCaptor = ArgumentCaptor.forClass(Budget.class);

    private final YearMonth defaultYearMonth = YearMonth.now();

    @Test
    void callsServiceWithDefaultBudgetYearMonthWhenNoneProvided() throws Exception {
      // Given a request with no budgetYearMonth
      // When the request is made
      mockMvc.perform(get("/budgets"));

      // Then the service should be called one time with the default budgetYearMonth
      verify(service, times(1)).getFor(defaultYearMonth);
    }

    @ParameterizedTest
    @ValueSource(strings = { "2023-09-01", "2023.01", "2023.1", "23-1", "2023/01", "2023/1", "01-23" })
    void returns400WhenBudgetYearMonthIsNotCorrectlyFormatted(String invalidYearMonthString) throws Exception {
      // Given a request with an invalid budgetYearMonthString
      // When the request is made
      mockMvc.perform(get("/budgets?budgetYearMonth=%s".formatted(invalidYearMonthString)))
              // Then the response should be a 400
              .andExpect(status().isBadRequest());
      // And the service should not be called
      verify(service, never()).getFor(any());
    }

    @ParameterizedTest
    @ValueSource(strings = { "2021-09", "2021-9" })
    void callsServiceMethodWithStartingDateFromParameter(String budgetYearMonth) throws Exception {
      // Given a correctly formatted budgetYearMonthString
      // When the request is made
      mockMvc.perform(get("/budgets").param("budgetYearMonth", budgetYearMonth));

      // Then the service should be called with the correct budgetYearMonth
      verify(service, times(1)).getFor(YearMonth.of(2021, 9));
    }

    @Test
    void callsMapperWithCorrectDomainObject() throws Exception {
      // Given a Budget returned from the service
      Budget budget = new Budget(
              YearMonth.of(2021, 9),
              List.of(
                      new LineItem(
                              1L,
                              YearMonth.of(2021, 9),
                              "Name 1",
                              120000,
                              Category.FOOD,
                              "description 1",
                              lastModifiedAt,
                              List.of())));
      when(service.getFor(defaultYearMonth)).thenReturn(budget);

      // When the request is made
      mockMvc.perform(get("/budgets"));

      // Then the mapper should be called one time
      verify(mapper, times(1)).mapToResponse(domainCaptor.capture());
      // And the mapper should be called with the correct domain object
      assertTrue(domainCaptor.getAllValues().contains(budget));
    }

    @Test
    void returnsCorrectBodyStringComparison() throws Exception {
      // Given a Budget returned from the service
      Budget budget = new Budget(
              YearMonth.of(2021, 9),
              List.of(
                      new LineItem(
                              1L,
                              YearMonth.of(2021, 9),
                              "Name 1",
                              120000,
                              Category.FOOD,
                              "description 1",
                              lastModifiedAt,
                              List.of())));
      when(service.getFor(defaultYearMonth)).thenReturn(budget);
      // And a BudgetResponse returned from the mapper
      BudgetResponse response = new BudgetResponse(
              YearMonth.of(2021, 9),
              List.of(
                      new LineItemResponse(
                              1L,
                              YearMonth.of(2021, 9),
                              "Name",
                              100,
                              Category.INCOME,
                              "Description",
                              lastModifiedAt,
                              0,
                              0.0,
                              100,
                              List.of())),
              List.of(),
              List.of(),
              List.of(),
              List.of(),
              List.of(),
              List.of(),
              List.of(),
              List.of(),
              List.of(),
              List.of(),
              100,
              0,
              100,
              0,
              0,
              0
      );
      when(mapper.mapToResponse(any())).thenReturn(response);

      // When the request is made
      String jsonBody = mockMvc.perform(get("/budgets"))
              // Then the response should be a 200
              .andExpect(status().isOk())
              .andReturn()
              .getResponse()
              .getContentAsString();
      // And the response body should contain the correct json
      assertEquals("{\"budgetDate\":\"2021-09\",\"incomes\":[{\"id\":1,\"budgetDate\":\"2021-09\",\"name\":\"Name\"," +
              "\"plannedAmount\":1.00,\"category\":\"INCOME\",\"description\":\"Description\"," +
              "\"lastModifiedAt\":\"2022-09-21T23:31:04.206157Z\",\"totalTransactions\":0.00," +
              "\"percentageOfPlanned\":0.0,\"totalRemaining\":1.00,\"transactions\":[]}],\"savings\":[]," +
              "\"investments\":[],\"housing\":[],\"utilities\":[],\"transportation\":[],\"food\":[],\"personal\":[]," +
              "\"health\":[],\"lifestyle\":[],\"debts\":[],\"totalPlannedIncome\":1.00,\"totalReceived\":0.00," +
              "\"totalPlannedExpense\":1.00,\"totalSpent\":0.00,\"totalRemaining\":0.00," +
              "\"totalLeftToBudget\":0.00}", jsonBody);
    }
  }

  //  @Nested
//  class CreateFor {
//    private final LineItemResponse responseDto = new LineItemResponse(
//            1L,
//            YearMonth.of(2021, 9),
//            "Name",
//            120000,
//            Category.FOOD,
//            "description",
//            lastModifiedAt,
//            0,
//            0.0,
//            120000,
//            List.of());
//
//    private final String validContent = """
//            {
//              "budgetDate": "2021-09",
//              "name": "Name",
//              "plannedAmount": 1200.00,
//              "category": "FOOD",
//              "description": "description"
//            }
//            """;
//
//    // One test to ensure the request object is validated.
//    // The validation tests are in another file. If I were to check each validation annotation at this level, I would
//    // have to duplicate the checks for each endpoint that accepts a Request object.
//    // More Info in 'Verify Field Validation' section of https://www.arhohuttunen.com/spring-boot-webmvctest/
//    @Test
//    void returnsBadRequestWhenNotValid() throws Exception {
//      // Given a request with invalid content
//      String invalidContent = """
//              {
//                "budgetDate": null,
//                "name": "Name",
//                "plannedAmount": 1200.00,
//                "category": "FOOD",
//                "description": "description"
//              }
//              """;
//
//      // When the request is made
//      mockMvc.perform(post("/line-items")
//                      .contentType(MediaType.APPLICATION_JSON)
//                      .content(invalidContent))
//              // Then the response should be a 400
//              .andExpect(status().isBadRequest());
//    }
//
//    // Testing the JsonFormat here. This annotation deals with deserialization, not validation. So it is appropriate
//    // to test it here.
//    @ParameterizedTest
//    @NullAndEmptySource
//    @ValueSource(strings = { "2023-01-02", "01-2023", "1, 2023", "01/2023", "2023/01" })
//    void returnsBadRequestWhenInvalidDateString(String invalidDateString) throws Exception {
//      // Given request content with an invalid budgetDate string
//      String requestContent = """
//              {
//                "budgetDate": "%s",
//                "name": "Name",
//                "plannedAmount": 2500.00,
//                "category": "FOOD",
//                "description": "description"
//              }
//              """.formatted(invalidDateString);
//
//      // When the request is made
//      mockMvc.perform(post("/line-items")
//                      .contentType(MediaType.APPLICATION_JSON)
//                      .content(requestContent))
//              // Then the response should be a 400
//              .andExpect(status().isBadRequest());
//    }
//
//    @ParameterizedTest
//    @ValueSource(strings = { "2023-01", "2023-1" })
//    void returnsCreatedWhenValidDateString(String date) throws Exception {
//      // Given valid budgetDate in the request content
//      String requestContent = """
//              {
//                "budgetDate": "%s",
//                "name": "Name",
//                "plannedAmount": 2500.00,
//                "category": "FOOD",
//                "description": "description"
//              }
//              """.formatted(date);
//      // And a response dto returned from the mapper (declared at class-level)
//      when(mapper.mapToResponse(any())).thenReturn(responseDto);
//
//      // When the request is made
//      mockMvc.perform(post("/line-items")
//                      .contentType(MediaType.APPLICATION_JSON)
//                      .content(requestContent))
//              // Then the response should be a 201
//              .andExpect(status().isCreated());
//    }
//
//    @Test
//    void returnsCorrectStatusAndHeaders() throws Exception {
//      // Given a response dto returned from the mapper (declared at class-level)
//      when(mapper.mapToResponse(any())).thenReturn(responseDto);
//
//      // When the request is made
//      mockMvc.perform(post("/line-items")
//                      .contentType(MediaType.APPLICATION_JSON)
//                      .content(validContent))
//              // Then the response should be a 201
//              .andExpect(status().isCreated())
//              // And the response should contain the correct Location header
//              .andExpect(header().string("Location", "/line-items/%d".formatted(id)))
//              // And the response should contain the correct Last-Modified header
//              .andExpect(header().string("Last-Modified", httpHeaderDateTimeFormatter.format(lastModifiedAt)));
//    }
//
//    // The next three tests are redundant. They all cover the deserialization of the response body.
//    // Each one has some pros and cons. I am keeping all three because over time I would like to see if one
//    // stands out by catching more bugs with the fewest false positives.
//    @Test
//    void returnsCorrectBodyJsonPath() throws Exception {
//      // Given a response dto returned from the mapper (declared at class-level)
//      when(mapper.mapToResponse(any())).thenReturn(responseDto);
//
//      // When the request is made
//      mockMvc.perform(post("/line-items")
//                      .contentType(MediaType.APPLICATION_JSON)
//                      .content(validContent))
//              // Then the response should be a 201
//              .andExpect(status().isCreated())
//              // And the response body should contain the correct data
//              .andExpect(jsonPath("$.id").value(1L))
//              .andExpect(jsonPath("$.budgetDate").value("2021-09"))
//              .andExpect(jsonPath("$.name").value("Name"))
//              .andExpect(jsonPath("$.plannedAmount").value(1200.00)) // should've been converted to dollars
//              .andExpect(jsonPath("$.category").value("FOOD"))
//              .andExpect(jsonPath("$.description").value("description"))
//              .andExpect(jsonPath("$.lastModifiedAt").value(lastModifiedAt.toString()))
//              .andExpect(jsonPath("$.totalTransactions").value(0.00))
//              .andExpect(jsonPath("$.percentageOfPlanned").value(0.0))
//              .andExpect(jsonPath("$.totalRemaining").value(1200.00)) // should've been converted to dollars
//              .andExpect(jsonPath("$.transactions").isEmpty());
//    }
//
//    @Test
//    void returnsCorrectBodyObjectMapper() throws Exception {
//      // Given a response dto returned from the mapper (declared at class-level)
//      when(mapper.mapToResponse(any())).thenReturn(responseDto);
//
//      // When the request is made
//      mockMvc.perform(post("/line-items")
//                      .contentType(MediaType.APPLICATION_JSON)
//                      .content(validContent))
//              // Then the response should be a 201
//              .andExpect(status().isCreated())
//              // And the response should match the responseDto deserialized by ObjectMapper
//              .andExpect(content().string(objectMapper.writeValueAsString(responseDto)));
//    }
//
//    @Test
//    void returnsCorrectBodyStringComparison() throws Exception {
//      // Given a response dto returned from the mapper (declared at class-level)
//      when(mapper.mapToResponse(any())).thenReturn(responseDto);
//
//      // When the request is made
//      String jsonBody = mockMvc.perform(post("/line-items")
//                      .contentType(MediaType.APPLICATION_JSON)
//                      .content(validContent))
//              // Then the response should be a 201
//              .andExpect(status().isCreated())
//              .andReturn()
//              .getResponse()
//              .getContentAsString();
//      // And the response body should contain the correct json
//      assertEquals("{\"id\":1,\"budgetDate\":\"2021-09\",\"name\":\"Name\",\"plannedAmount\":1200.00," +
//              "\"category\":\"FOOD\",\"description\":\"description\"," +
//              "\"lastModifiedAt\":\"2022-09-21T23:31:04.206157Z\",\"totalTransactions\":0.00," +
//              "\"percentageOfPlanned\":0.0,\"totalRemaining\":1200.00,\"transactions\":[]}", jsonBody);
//    }
//  }
}
