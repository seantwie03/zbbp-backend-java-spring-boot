package me.seantwiehaus.zbbp.dao.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import me.seantwiehaus.zbbp.domain.Transaction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.Objects;

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
  @Column(name = "date", nullable = false)
  private LocalDate date;
  @NotBlank
  @Column(name = "merchant", nullable = false)
  private String merchant;
  @Column(name = "amount", nullable = false)
  private Integer amount;
  @Column(name = "is_income", nullable = false)
  private boolean isIncome;
  @Column(name = "description")
  private String description;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "line_item_id")
  private LineItemEntity lineItemEntity;

  public TransactionEntity(Transaction transaction) {
    this.id = transaction.getId();
    this.date = transaction.getDate();
    this.merchant = transaction.getMerchant();
    this.amount = transaction.getAmount().inCents();
    this.isIncome = transaction.isIncome();
    this.setLineItemEntity(new LineItemEntity(transaction.getLineItemId()));
    this.description = transaction.getDescription();
  }

  public Transaction convertToTransaction() {
    return new Transaction(
        id,
        date,
        merchant,
        amount,
        isIncome,
        lineItemEntity.getId(),
        description,
        lastModifiedAt);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TransactionEntity that = (TransactionEntity) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
