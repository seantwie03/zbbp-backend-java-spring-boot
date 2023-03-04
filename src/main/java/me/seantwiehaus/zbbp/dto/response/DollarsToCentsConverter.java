package me.seantwiehaus.zbbp.dto.response;

public class DollarsToCentsConverter {
  private DollarsToCentsConverter() {
  }

  public static int convert(double dollars) {
    return Math.toIntExact(Math.round(dollars * 100));
  }
}
