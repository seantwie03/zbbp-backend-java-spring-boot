package me.seantwiehaus.zbbp.dto.response;

class CentsToDollarsConverter {
  private CentsToDollarsConverter() {
  }

  public static double convert(int cents) {
    return cents / 100.0;
  }
}
