package me.seantwiehaus.zbbp.dao.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
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
