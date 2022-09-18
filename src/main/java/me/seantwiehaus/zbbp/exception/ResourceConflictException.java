package me.seantwiehaus.zbbp.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ResourceConflictException extends RuntimeException {
    public ResourceConflictException(String message) {
        super(message);
        log.error(message);
    }

}
