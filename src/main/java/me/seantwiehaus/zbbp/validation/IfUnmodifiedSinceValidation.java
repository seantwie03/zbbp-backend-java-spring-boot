package me.seantwiehaus.zbbp.validation;

import me.seantwiehaus.zbbp.dao.entity.BaseEntity;
import me.seantwiehaus.zbbp.exception.ResourceConflictException;

import java.time.Instant;
import java.util.Objects;

public class IfUnmodifiedSinceValidation {
  private IfUnmodifiedSinceValidation() {
  }

  public static <T extends BaseEntity> void throwWhenEntityLastModifiedAtIsAfterIfUnmodifiedSince(
      T entity, Instant ifUnmodifiedSince) {
    // Null checks because this is a utility method that may be used in a variety of contexts
    Objects.requireNonNull(entity, "entity must not be null");
    Objects.requireNonNull(
        entity.getLastModifiedAt(),
        "%s.lastModifiedAt must not be null".formatted(entity.getClass().getSimpleName()));
    Objects.requireNonNull(ifUnmodifiedSince, "ifUnmodifiedSince must not be null");
    if (entity.getLastModifiedAt().isAfter(ifUnmodifiedSince)) {
      throw new ResourceConflictException(
          "%s with ID %d was modified after the provided If-Unmodified-Since header value of %s"
              .formatted(entity.getClass().getSimpleName(), entity.getId(), ifUnmodifiedSince));
    }
  }
}
