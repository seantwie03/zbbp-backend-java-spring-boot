package me.seantwiehaus.zbbp.dto.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

/**
 * Convert Dollars to Cents when deserializing the object from JSON.
 * This custom deserializer is no longer used. It worked great, but it caused some problems with the OpenApi
 * document generator. Since this custom deserializer took in a Double and returned an Integer, the OpenApi
 * document generator thought this API endpoint only accepted Integers. I guess it looked at the type the API endpoint
 * accepts without taking custom deserializers into account. Hopefully this will be fixed at some point and I can use
 * this again. For now, I'm using a DollarsToCentsMapper with MapStruct to perform the conversion.
 * I am leaving this here for future reference (this is a reference project after all).
 */
public class DollarsToCentsDeserializer extends JsonDeserializer<Integer> {
  @Override
  public Integer deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
    double dollars = p.getValueAsDouble();
    return Math.toIntExact(Math.round(dollars * 100));
  }
}
