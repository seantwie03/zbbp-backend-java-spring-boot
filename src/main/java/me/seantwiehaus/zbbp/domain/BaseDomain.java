package me.seantwiehaus.zbbp.domain;

import lombok.Getter;

import java.time.Instant;

@Getter
public class BaseDomain {
    protected final Instant lastModifiedAt;

    public BaseDomain(Instant lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
    }
}
