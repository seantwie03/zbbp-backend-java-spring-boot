package me.seantwiehaus.zbbp.domain;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.time.Instant;

@Getter
public class BaseDomain {
  protected final ItemType type;
  protected final Instant lastModifiedAt;

  public BaseDomain(@NotNull ItemType type, Instant lastModifiedAt) {
    this.type = type;
    this.lastModifiedAt = lastModifiedAt;
  }
}
