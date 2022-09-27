package me.seantwiehaus.zbbp.domain;

import lombok.Getter;

import java.time.Instant;

@Getter
public class BaseDomain {
  protected final ItemType type;
  protected final Instant lastModifiedAt;

  public BaseDomain(ItemType type, Instant lastModifiedAt) {
    this.type = type;
    this.lastModifiedAt = lastModifiedAt;
  }
}
