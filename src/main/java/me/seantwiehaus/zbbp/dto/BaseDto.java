package me.seantwiehaus.zbbp.dto;

import lombok.Getter;

import java.time.Instant;

@Getter
public class BaseDto {

    protected Integer version;

    protected Instant createdAt;

    protected Instant modifiedAt;

    public BaseDto(Integer version,
                   Instant createdAt,
                   Instant modifiedAt) {
        this.version = version;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
