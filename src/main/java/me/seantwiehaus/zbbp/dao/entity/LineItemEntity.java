package me.seantwiehaus.zbbp.dao.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import me.seantwiehaus.zbbp.dao.converter.YearMonthDateAttributeConverter;
import me.seantwiehaus.zbbp.domain.Category;
import me.seantwiehaus.zbbp.domain.LineItem;
import org.hibernate.annotations.ColumnTransformer;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@NamedEntityGraph(name = "lineItem.transactions", attributeNodes = {
    @NamedAttributeNode("transactionEntities"),
})
@Table(
    name = "line_items",
    uniqueConstraints = { @UniqueConstraint(columnNames = { "name", "budget_date", "category" }) })
public class LineItemEntity extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "id", nullable = false)
  private Long id;
  @Column(name = "budget_date", nullable = false)
  @Convert(converter = YearMonthDateAttributeConverter.class)
  private YearMonth budgetDate;
  @NotBlank
  @Column(name = "name", nullable = false)
  private String name;
  @Column(name = "planned_amount_cents", nullable = false)
  private int plannedAmount;
  @Column(name = "category", nullable = false)
  @ColumnTransformer(read = "upper(category)", write = "upper(?)")
  @Enumerated(EnumType.STRING)
  private Category category;
  @Column(name = "description")
  private String description;

  @OneToMany
  @JoinColumn(name = "line_item_id")
  @OrderBy("date asc, amount desc")
  private List<TransactionEntity> transactionEntities = new ArrayList<>();

  public LineItemEntity(LineItem lineItem) {
    this.id = lineItem.getId();
    this.budgetDate = lineItem.getBudgetDate();
    this.name = lineItem.getName();
    this.plannedAmount = lineItem.getPlannedAmount();
    this.category = lineItem.getCategory();
    this.description = lineItem.getDescription();
  }

  public LineItem convertToLineItem() {
    return new LineItem(
        id,
        type,
        budgetDate,
        name,
        plannedAmount,
        category,
        description,
        transactionEntities
            .stream()
            .map(TransactionEntity::convertToTransaction)
            .toList(),
        lastModifiedAt);
  }

  public static List<LineItem> convertToLineItems(List<LineItemEntity> lineItems) {
    return lineItems
        .stream()
        .map(LineItemEntity::convertToLineItem)
        .toList();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    LineItemEntity that = (LineItemEntity) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
