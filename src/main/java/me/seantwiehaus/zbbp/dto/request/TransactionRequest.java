package me.seantwiehaus.zbbp.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record TransactionRequest(
        // This pattern works for both "2023-3-2" and "2023-03-02"
        @NotNull @JsonFormat(pattern = "yyyy-M-d") LocalDate date,
        @NotBlank String merchant,
        // The desired behavior when the API request contains a null amount is for an exception to be thrown.
        // That behavior only occurs when using Double. If using double, the null value will deserialize to 0.0.
        // This amount is converted to cents (Integer) during the conversion from Request to Domain object. Therefore,
        // the max value is the max value of an Integer divided by 100 minus 1.
        // Example: $21,474,836.47 when converted to cents is 214_7483_647. This is the max value of an Integer. If
        // someone submits a value >= $21,474,836.47, the conversion will integer overflow.
        // In this application context it is not reasonable to expect a user to submit a transaction with an amount,
        // so it is capped at 21_474_835.
        @NotNull @Min(0) @Max(Integer.MAX_VALUE / 100 - 1) Double amount,
        Long lineItemId,
        String description) {
}
