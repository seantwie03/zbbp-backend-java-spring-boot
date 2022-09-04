package me.seantwiehaus.zbbp.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class Category {
    @NotNull
    private Long id;
    @NotBlank
    private String name;
    @NotNull
    private BigDecimal amount;
    @NotNull
    private LocalDate budgetDate;
    @NotNull
    private CategoryGroup categoryGroup;
    private List<Transaction> transactions;
}
