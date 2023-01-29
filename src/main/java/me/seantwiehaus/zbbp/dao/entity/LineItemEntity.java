package me.seantwiehaus.zbbp.dao.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import me.seantwiehaus.zbbp.dao.converter.YearMonthDateAttributeConverter;
import me.seantwiehaus.zbbp.domain.Category;
import org.hibernate.annotations.ColumnTransformer;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@NamedEntityGraph(name = "lineItem.transactions", attributeNodes = {
        @NamedAttributeNode("transactions"),
})
@Table(
        name = "line_items",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "budget_date", "category"})})
public class LineItemEntity extends BaseEntity {
  @NotNull
  @Column(name = "budget_date", nullable = false)
  @Convert(converter = YearMonthDateAttributeConverter.class)
  private YearMonth budgetDate;
  @NotBlank
  @Column(name = "name", nullable = false, length = 50)
  private String name;
  @Min(0)
  @Column(name = "planned_amount_cents", nullable = false)
  private int plannedAmount;
  @NotNull
  @Column(name = "category", nullable = false, length = 20)
  @ColumnTransformer(read = "upper(category)", write = "upper(?)")
  @Enumerated(EnumType.STRING)
  private Category category;
  @Column(name = "description")
  private String description;

  @OneToMany
  @JoinColumn(name = "line_item_id")
  @OrderBy("date asc, amount desc")
  @Builder.Default
  private List<TransactionEntity> transactions = new ArrayList<>();

  /**
   * Equal when the name, budget date, and category are equal.
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    LineItemEntity other = (LineItemEntity) obj;
    return Objects.equals(budgetDate, other.budgetDate)
            && Objects.equals(name, other.name)
            && category == other.category;
  }

  @Override
  public int hashCode() {
    return 13;
  }
}
