package me.seantwiehaus.zbbp.dto.serialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

/**
 * Convert Dollars to Cents when deserializing the object from JSON
 */
public class DollarsToCentsDeserializer extends JsonDeserializer<Integer> {
  @Override
  public Integer deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
    double dollars = p.getValueAsDouble();
    return Math.toIntExact(Math.round(dollars * 100));
  }
}
