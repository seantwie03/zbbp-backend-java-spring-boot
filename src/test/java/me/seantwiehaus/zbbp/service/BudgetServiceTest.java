package me.seantwiehaus.zbbp.service;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.YearMonth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BudgetServiceTest {

  @Mock
  LineItemService lineItemService;

  @InjectMocks
  BudgetService budgetService;

  @Captor
  ArgumentCaptor<YearMonth> yearMonthCaptor = ArgumentCaptor.forClass(YearMonth.class);

  @Nested
  class GetFor {
    @Test
    void callsLineItemServiceWithCorrectYearMonth() {
      // Given a YearMonth
      YearMonth yearMonth = YearMonth.of(2023, 3);

      // When the method under test is called
      budgetService.getFor(yearMonth);

      // Then LineItemService should be called one time
      verify(lineItemService, times(1)).getAllByYearMonth(yearMonthCaptor.capture());
      // And the YearMonth passed to LineItemService should be the same as the one passed to the method under test
      assertEquals(yearMonth, yearMonthCaptor.getValue());
    }
  }
}
