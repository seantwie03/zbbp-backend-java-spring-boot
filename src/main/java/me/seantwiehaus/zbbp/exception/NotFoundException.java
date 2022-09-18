package me.seantwiehaus.zbbp.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NotFoundException extends RuntimeException {
    public NotFoundException(String domainName, Long id) {
        super("Unable to find a " + domainName + " with Id: " + id);
        log.error("Unable to find a " + domainName + " with Id: " + id);
    }
}
