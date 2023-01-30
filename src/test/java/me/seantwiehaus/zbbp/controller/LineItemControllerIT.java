package me.seantwiehaus.zbbp.controller;

import me.seantwiehaus.zbbp.domain.Category;
import me.seantwiehaus.zbbp.domain.LineItem;
import me.seantwiehaus.zbbp.mapper.LineItemMapper;
import me.seantwiehaus.zbbp.service.LineItemService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
  private final DateTimeFormatter rfc1123Formatter = DateTimeFormatter.RFC_1123_DATE_TIME.withZone(ZoneOffset.UTC);

  @Nested
  class GetAllLineItemsBetween {
    @Captor
    ArgumentCaptor<LineItem> domainCaptor = ArgumentCaptor.forClass(LineItem.class);

    private final YearMonth defaultStartingDate = YearMonth.now();
    private final YearMonth defaultEndingDate = YearMonth.now();

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
      mockMvc.perform(get("/line-items?startingBudgetDate=%s".formatted(startingDate)));

      // Then the service should be called with the correct parameters
      verify(service, times(1)).getAllBetween(YearMonth.of(2021, 9), defaultEndingDate);
    }

    @Test
    void callsServiceMethodWithEndingDateFromParameter() throws Exception {
      // Given a correctly formatted endingDate
      String endingDate = "2021-09";

      // When the request is made
      mockMvc.perform(get("/line-items?endingBudgetDate=%s".formatted(endingDate)));

      // Then the service should be called with the correct parameters
      verify(service, times(1)).getAllBetween(defaultStartingDate, YearMonth.of(2021, 9));
    }

    @Test
    void callsMapperWithCorrectDomainObjectsInCorrectOrder() throws Exception {
      // Given two LineItems returned from the service
      LineItem lineItem1 = createDomain().id(1L).budgetDate(YearMonth.of(2021, 9)).build();
      LineItem lineItem2 = createDomain().id(1L).budgetDate(YearMonth.of(2021, 8)).build();
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
  }

  private LineItem.LineItemBuilder createDomain() {
    return LineItem.builder(YearMonth.of(2021, 9), "Name", 140000, Category.FOOD)
            .id(1L)
            .description("Description")
            .lastModifiedAt(lastModifiedAt);
  }
}
