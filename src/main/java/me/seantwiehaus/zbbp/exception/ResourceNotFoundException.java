package me.seantwiehaus.zbbp.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ResourceNotFoundException extends RuntimeException {
  public ResourceNotFoundException(String domainName, Long id) {
    super("Unable to find a %s with ID=%d".formatted(domainName, id));
    log.error("Unable to find a %s with ID=%d".formatted(domainName, id));
  }
}
