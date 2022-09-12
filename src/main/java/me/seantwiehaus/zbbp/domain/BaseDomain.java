package me.seantwiehaus.zbbp.domain;

import lombok.Getter;

import java.time.Instant;

@Getter
public class BaseDomain {
    protected final int version;
    protected final Instant createdAt;
    protected final Instant lastModifiedAt;

    public BaseDomain(int version,
                      Instant createdAt,
                      Instant lastModifiedAt) {
        this.version = version;
        this.createdAt = createdAt;
        this.lastModifiedAt = lastModifiedAt;
    }
}
