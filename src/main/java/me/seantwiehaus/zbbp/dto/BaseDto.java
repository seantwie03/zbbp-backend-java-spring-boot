package me.seantwiehaus.zbbp.dto;

import lombok.Getter;

import java.time.Instant;

@Getter
public class BaseDto {

    protected int version;

    protected Instant createdAt;

    protected Instant modifiedAt;

    public BaseDto(int version,
                   Instant createdAt,
                   Instant modifiedAt) {
        this.version = version;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
