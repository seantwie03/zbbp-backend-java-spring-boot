package me.seantwiehaus.zbbp.exception;

import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Log
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No results found")
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
        log.severe(message);
    }
}
