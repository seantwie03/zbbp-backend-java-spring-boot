package me.seantwiehaus.zbbp.dto.response;

import lombok.Getter;
import lombok.ToString;
import me.seantwiehaus.zbbp.domain.Transaction;

import java.time.LocalDate;

@Getter
@ToString
public class TransactionResponse extends BaseResponse {
  private final Long id;
  private final Double amount;
  private final LocalDate date;
  private final String description;
  private final Long categoryId;

  public TransactionResponse(Transaction transaction) {
    super(transaction.getLastModifiedAt());
    this.id = transaction.getId();
    this.amount = transaction.getAmount().inDollars();
    this.date = transaction.getDate();
    this.description = transaction.getDescription();
    this.categoryId = transaction.getCategoryId();
  }

  public Transaction convertToTransaction() {
    return new Transaction(
        lastModifiedAt,
        id,
        amount,
        date,
        description,
        categoryId);
  }
}
