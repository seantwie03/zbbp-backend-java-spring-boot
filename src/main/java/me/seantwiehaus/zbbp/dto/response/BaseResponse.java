package me.seantwiehaus.zbbp.dto.response;

import lombok.Getter;
import me.seantwiehaus.zbbp.domain.ItemType;

import java.time.Instant;

@Getter
public class BaseResponse {
  protected ItemType type;
  protected Instant lastModifiedAt;

  public BaseResponse(ItemType type, Instant lastModifiedAt) {
    this.type = type;
    this.lastModifiedAt = lastModifiedAt;
  }
}
