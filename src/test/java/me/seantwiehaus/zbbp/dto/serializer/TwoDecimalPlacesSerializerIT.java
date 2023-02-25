package me.seantwiehaus.zbbp.dto.serializer;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import java.io.IOException;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class TwoDecimalPlacesSerializerIT {
  @Autowired
  JacksonTester<AmountResponse> jacksonTester;

  private record AmountResponse(@JsonSerialize(using = TwoDecimalPlacesSerializer.class) Double amount) {}

  @ParameterizedTest(name = "{index} => {0} rounded to two decimal places {1}")
  @MethodSource
  void serializeCentsToDollars(double cents, double dollars) throws IOException {
    AmountResponse amountResponse = new AmountResponse(cents);

    JsonContent<AmountResponse> json = jacksonTester.write(amountResponse);

    assertThat(json)
            .extractingJsonPathNumberValue("$.amount")
            .isEqualTo(dollars);
  }

  private static Stream<Arguments> serializeCentsToDollars() {
    return Stream.of(
            Arguments.of((double) 1, 1.00),
            Arguments.of(1.0, 1.00),
            Arguments.of(1.00, 1.00),
            Arguments.of(1.000, 1.00),
            Arguments.of(1.006, 1.01),
            Arguments.of(1.0006, 1.00)
    );
  }
}
