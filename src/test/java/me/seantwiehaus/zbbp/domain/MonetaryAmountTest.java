package me.seantwiehaus.zbbp.domain;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MonetaryAmountTest {
  private static MonetaryAmount monetaryAmount1;
  private static MonetaryAmount monetaryAmount2;

  @BeforeAll
  static void setup() {
    monetaryAmount1 = new MonetaryAmount(25.50);
    monetaryAmount2 = new MonetaryAmount(2550);
  }

  @Test
  void shouldReturnCorrectValueInCents() {
    assertEquals(2550, monetaryAmount1.inCents());
    assertEquals(2550, monetaryAmount2.inCents());
  }

  @Test
  void shouldReturnCorrectValueInDollars() {
    assertEquals(25.50, monetaryAmount1.inDollars());
    assertEquals(25.50, monetaryAmount2.inDollars());
  }

  @Test
  void shouldHaveEqualityWhenCreatedWithDifferentConstructors() {
    assertEquals(monetaryAmount1, monetaryAmount2);
  }
}
