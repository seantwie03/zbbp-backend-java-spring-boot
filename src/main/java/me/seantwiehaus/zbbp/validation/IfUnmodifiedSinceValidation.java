package me.seantwiehaus.zbbp.validation;

import me.seantwiehaus.zbbp.dao.entity.BaseEntity;
import me.seantwiehaus.zbbp.exception.ResourceConflictException;

import java.time.Instant;

public class IfUnmodifiedSinceValidation {
  private IfUnmodifiedSinceValidation() {
  }

  public static <T extends BaseEntity> void throwWhenEntityLastModifiedAtIsAfterIfUnmodifiedSince(
      T entity, Instant ifUnmodifiedSince) {
    if (entity.getLastModifiedAt().isAfter(ifUnmodifiedSince)) {
      throw new ResourceConflictException(
          "%s with ID=%d was modified after the provided If-Unmodified-Since header value of %s"
              .formatted(entity.getClass().getSimpleName(), entity.getId(), ifUnmodifiedSince));
    }
  }
}
