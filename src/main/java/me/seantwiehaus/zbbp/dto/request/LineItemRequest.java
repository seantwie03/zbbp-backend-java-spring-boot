package me.seantwiehaus.zbbp.dto.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import me.seantwiehaus.zbbp.domain.Category;
import me.seantwiehaus.zbbp.dto.serialize.DollarsToCentsDeserializer;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.YearMonth;

public record LineItemRequest(
    @NotNull YearMonth budgetDate,
    @NotBlank String name,
    @NotNull @Min(0) @JsonDeserialize(using = DollarsToCentsDeserializer.class) Integer plannedAmount,
    @NotNull Category category,
    String description) {
}
