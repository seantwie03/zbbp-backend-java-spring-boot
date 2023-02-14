package me.seantwiehaus.zbbp.dto.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import me.seantwiehaus.zbbp.domain.Category;
import me.seantwiehaus.zbbp.dto.serializer.DollarsToCentsDeserializer;

import java.time.YearMonth;

public record LineItemRequest(
        @NotNull YearMonth budgetDate,
        @NotBlank String name,
        // The desired behavior when the API request contains a null amount is for an exception to be thrown.
        // That behavior only occurs when using Integer. If using int, the null value will deserialize to 0.
        @NotNull @Min(0) @JsonDeserialize(using = DollarsToCentsDeserializer.class) Integer plannedAmount,
        @NotNull Category category,
        String description) {
}
