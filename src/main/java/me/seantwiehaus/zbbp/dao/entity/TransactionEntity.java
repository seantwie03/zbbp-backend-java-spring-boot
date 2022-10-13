package me.seantwiehaus.zbbp.dao.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import me.seantwiehaus.zbbp.domain.Transaction;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "transactions")
public class TransactionEntity extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;
  @NotNull
  @Column(name = "date", nullable = false)
  private LocalDate date;
  @NotBlank
  @Column(name = "merchant", nullable = false, length = 50)
  private String merchant;
  @Min(0)
  @Column(name = "amount_cents", nullable = false)
  private int amount;
  @Min(0)
  @Column(name = "line_item_id")
  private Long lineItemId;
  @Column(name = "description")
  private String description;

  public TransactionEntity(Transaction transaction) {
    this.id = transaction.getId();
    this.date = transaction.getDate();
    this.merchant = transaction.getMerchant();
    this.amount = transaction.getAmount();
    this.setLineItemId(transaction.getLineItemId());
    this.description = transaction.getDescription();
  }

  public Transaction convertToTransaction() {
    return new Transaction(
        id,
        type,
        date,
        merchant,
        amount,
        lineItemId,
        description,
        lastModifiedAt);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    TransactionEntity other = (TransactionEntity) obj;
    return id != null && id.equals(other.id);
  }

  @Override
  public int hashCode() {
    return 59;
  }
}
