package me.seantwiehaus.zbbp.dto.response;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CentsToDollarsConverterTest {

  @ParameterizedTest(name = "{index} => {0} cents converts to {1} dollars")
  @MethodSource
  void convertsCentsToDollars(int cents, double dollars) {
    assertEquals(dollars, CentsToDollarsConverter.convert(cents));
  }

  private static Stream<Arguments> convertsCentsToDollars() {
    return Stream.of(
            Arguments.of(0, 0.00),
            Arguments.of(1, 0.01),
            Arguments.of(10, 0.10),
            Arguments.of(100, 1.00),
            Arguments.of(1000, 10.00)
    );
  }
}
