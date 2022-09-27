package me.seantwiehaus.zbbp.domain;

public enum ItemType {
  INCOME("INCOME"), EXPENSE("EXPENSE");

  private final String type;

  ItemType(String type) {
    this.type = type;
  }

  @Override
  public String toString() {
    return type.charAt(0) + type.substring(1).toLowerCase();
  }
}
