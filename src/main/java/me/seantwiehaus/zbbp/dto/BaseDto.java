package me.seantwiehaus.zbbp.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@Getter
@RequiredArgsConstructor
public class BaseDto {
    protected Instant lastModifiedAt;

    public BaseDto(Instant lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
    }
}
