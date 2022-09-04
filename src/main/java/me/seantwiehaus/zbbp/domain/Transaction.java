package me.seantwiehaus.zbbp.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Instant;

@Getter
@AllArgsConstructor
public class Transaction {
    @NotNull
    private Long id;
    @NotNull
    private BigDecimal amount;
    @NotNull
    private Instant timestamp;
    @NotBlank
    private String description;
    @NotNull
    private Category category;
}
