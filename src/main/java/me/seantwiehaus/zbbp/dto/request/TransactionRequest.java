package me.seantwiehaus.zbbp.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import me.seantwiehaus.zbbp.domain.Money;
import me.seantwiehaus.zbbp.domain.Transaction;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public class TransactionRequest {
    @NotNull
    @Min(0)
    private final Double amount;
    @NotNull
    private final LocalDate date;
    @NotBlank
    private final String description;
    private Long categoryId;

    public Transaction convertToTransaction() {
        return new Transaction(
                null,
                null,
                new Money(amount),
                date,
                description,
                categoryId);
    }
}
