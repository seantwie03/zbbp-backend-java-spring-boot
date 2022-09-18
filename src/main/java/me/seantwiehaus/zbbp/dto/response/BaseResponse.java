package me.seantwiehaus.zbbp.dto.response;

import lombok.Getter;

import java.time.Instant;

@Getter
public class BaseResponse {
    protected Instant lastModifiedAt;

    public BaseResponse(Instant lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
    }
}
