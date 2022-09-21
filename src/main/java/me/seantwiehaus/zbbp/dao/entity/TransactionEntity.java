package me.seantwiehaus.zbbp.dao.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import me.seantwiehaus.zbbp.domain.Transaction;

import javax.persistence.*;
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
  @Column(name = "amount", nullable = false)
  private Integer amount;
  @Column(name = "date", nullable = false)
  private LocalDate date;
  @Column(name = "description", nullable = false)
  private String description;
  @Column(name = "line_item_id")
  private Long lineItemId;

  public TransactionEntity(Transaction transaction) {
    this.id = transaction.getId();
    this.amount = transaction.getAmount().inCents();
    this.date = transaction.getDate();
    this.description = transaction.getDescription();
    this.setLineItemId(transaction.getLineItemId());
  }

  public Transaction convertToTransaction() {
    return new Transaction(
        lastModifiedAt,
        id,
        amount,
        date,
        description,
        lineItemId);
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
