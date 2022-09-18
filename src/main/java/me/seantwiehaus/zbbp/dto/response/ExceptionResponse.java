package me.seantwiehaus.zbbp.dto.response;

import lombok.Getter;

import java.time.Instant;

@Getter
public class ExceptionResponse {
    private final Instant timestamp = Instant.now();
    private final Integer status;
    private final String error;
    private final String path;

    public ExceptionResponse(Integer status,
                             String error,
                             String path) {
        this.status = status;
        this.error = error;
        this.path = path;
    }
}
