package me.seantwiehaus.zbbp.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class CategoryGroup {
    @NotNull
    private Long id;
    @NotBlank
    private String name;
    @NotNull
    private LocalDate budgetDate;
    private List<Category> categories;
}
