package me.seantwiehaus.zbbp.dto.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.DecimalFormat;

/**
 * Converts Cents to Dollars when serializing object to JSON
 */
public class CentsToDollarsSerializer extends JsonSerializer<Integer> {
  private static final DecimalFormat decimalFormat = new DecimalFormat("0.00");

  @Override
  public void serialize(Integer value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
    gen.writeNumber(decimalFormat.format(value / 100.0));
  }
}
