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
class CentsToDollarsSerializerIT {
  @Autowired
  JacksonTester<RecordResponse> jacksonTester;

  private record RecordResponse(@JsonSerialize(using = CentsToDollarsSerializer.class) Integer amount) {}

  @ParameterizedTest(name = "{index} => {0} cents converts to {1} dollars")
  @MethodSource
  void serializeCentsToDollars(int cents, double dollars) throws IOException {
    RecordResponse recordResponse = new RecordResponse(cents);

    JsonContent<RecordResponse> json = jacksonTester.write(recordResponse);

    assertThat(json)
            .extractingJsonPathNumberValue("$.amount")
            .isEqualTo(dollars);
  }

  private static Stream<Arguments> serializeCentsToDollars() {
    return Stream.of(
            Arguments.of(0, 0.00),
            Arguments.of(1, 0.01),
            Arguments.of(10, 0.10),
            Arguments.of(100, 1.00)
    );
  }
}
