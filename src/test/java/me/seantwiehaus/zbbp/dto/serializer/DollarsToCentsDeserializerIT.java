package me.seantwiehaus.zbbp.dto.serializer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class DollarsToCentsDeserializerIT {
  @Autowired
  JacksonTester<RecordRequest> jacksonTester;

  private record RecordRequest(@JsonDeserialize(using = DollarsToCentsDeserializer.class) Integer amount) {}

  @ParameterizedTest(name = "{index} => {0} dollars converts to {1} cents")
  @MethodSource
  void serializeDollarsToCents(double dollars, int cents) throws IOException {
    // Given a Json payload with an amount in dollars
    String json = "{\"amount\": " + dollars + "}\"";

    // When the Json is deserialized to a RecordRequest which uses the DollarsToCentsDeserializer
    RecordRequest returned = jacksonTester.parseObject(json);

    // Then the amount should be converted to cents
    assertThat(returned.amount).isEqualTo(new RecordRequest(cents).amount);
  }

  private static Stream<Arguments> serializeDollarsToCents() {
    return Stream.of(
            Arguments.of(0.000001, 0),
            Arguments.of(0.001, 0),
            Arguments.of(0.00, 0),
            Arguments.of(0.01, 1),
            Arguments.of(0.1, 10),
            Arguments.of(0.10, 10),
            Arguments.of(1, 100),
            Arguments.of(1.00, 100),
            Arguments.of(1.0000000, 100)
    );
  }
}
