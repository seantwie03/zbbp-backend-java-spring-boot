package me.seantwiehaus.zbbp.dto.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.DecimalFormat;

public class TwoDecimalPlacesSerializer extends JsonSerializer<Double> {
  private static final DecimalFormat decimalFormat = new DecimalFormat("0.00");

  @Override
  public void serialize(Double value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
    gen.writeNumber(decimalFormat.format(value));
  }
}
