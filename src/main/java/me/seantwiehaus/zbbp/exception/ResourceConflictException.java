package me.seantwiehaus.zbbp.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ResponseStatus(value = HttpStatus.CONFLICT, reason = "This record has been changed since you requested it.")
public class ResourceConflictException extends RuntimeException {
    public ResourceConflictException(String message) {
        super(message);
        log.error(message);
    }

}
