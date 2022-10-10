package me.seantwiehaus.zbbp.dto.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.ToString;
import me.seantwiehaus.zbbp.domain.Transaction;
import me.seantwiehaus.zbbp.dto.serialize.CentsToDollarsSerializer;

import java.time.LocalDate;

@Getter
@ToString
public class TransactionResponse extends BaseResponse {
  private final Long id;
  private final LocalDate date;
  private final String merchant;
  @JsonSerialize(using = CentsToDollarsSerializer.class)
  private final int amount;
  private final Long lineItemId;
  private final String description;

  public TransactionResponse(Transaction transaction) {
    super(transaction.getType(), transaction.getLastModifiedAt());
    this.id = transaction.getId();
    this.date = transaction.getDate();
    this.merchant = transaction.getMerchant();
    this.amount = transaction.getAmount();
    this.lineItemId = transaction.getLineItemId();
    this.description = transaction.getDescription();
  }
}
