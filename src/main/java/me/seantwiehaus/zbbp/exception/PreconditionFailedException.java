package me.seantwiehaus.zbbp.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PreconditionFailedException extends RuntimeException {
  public PreconditionFailedException(String message) {
    super(message);
    log.error(message);
  }
}
