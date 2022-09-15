package me.seantwiehaus.zbbp.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No results found")
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
        log.error(message);
    }
}
