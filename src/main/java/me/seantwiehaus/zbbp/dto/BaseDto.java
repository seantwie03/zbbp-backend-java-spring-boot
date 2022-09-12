package me.seantwiehaus.zbbp.dto;

import lombok.Getter;

import java.time.Instant;

@Getter
public class BaseDto {
    protected Instant modifiedAt;

    public BaseDto(Instant modifiedAt) {
        this.modifiedAt = modifiedAt;
    }
}
