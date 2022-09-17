package me.seantwiehaus.zbbp.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
public class ResourceConflictException extends ResponseStatusException {
    public ResourceConflictException(String message) {
        super(HttpStatus.CONFLICT, message);
        log.error(message);
    }

}
