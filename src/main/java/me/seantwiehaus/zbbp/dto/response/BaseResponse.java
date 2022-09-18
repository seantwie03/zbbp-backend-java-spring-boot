package me.seantwiehaus.zbbp.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@Getter
@RequiredArgsConstructor
public class BaseResponse {
    protected Instant lastModifiedAt;

    public BaseResponse(Instant lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
    }
}
