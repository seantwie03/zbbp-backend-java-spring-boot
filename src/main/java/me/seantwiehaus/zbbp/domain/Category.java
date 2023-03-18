package me.seantwiehaus.zbbp.domain;

public enum Category {
  INCOME("INCOME"),
  SAVINGS("SAVINGS"),
  INVESTMENTS("INVESTMENTS"),
  HOUSING("HOUSING"),
  TRANSPORTATION("TRANSPORTATION"),
  FOOD("FOOD"),
  // TODO: Add Utilities?
  PERSONAL("PERSONAL"),
  HEALTH("HEALTH"),
  LIFESTYLE("LIFESTYLE");

  private final String name;

  Category(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return name.charAt(0) + name.substring(1).toLowerCase();
  }
}
