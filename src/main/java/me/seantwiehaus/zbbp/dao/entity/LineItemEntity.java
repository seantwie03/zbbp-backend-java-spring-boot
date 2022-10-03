package me.seantwiehaus.zbbp.dao.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import me.seantwiehaus.zbbp.domain.BudgetMonth;
import me.seantwiehaus.zbbp.domain.Category;
import me.seantwiehaus.zbbp.domain.LineItem;

import javax.persistence.*;
import java.time.LocalDate;
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
@Table(name = "line_items", uniqueConstraints = { @UniqueConstraint(columnNames = { "name", "budget_date" }) })
public class LineItemEntity extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "id", nullable = false)
  private Long id;
  /**
   * BudgetDates only need the Year and Month; however, storing only the Year and Month in the database can be
   * tedious. Instead, the date is always set to the 1st.
   */
  @Column(name = "budget_date", nullable = false)
  private LocalDate budgetDate;
  private String name;
  @Column(name = "planned_amount", nullable = false)
  private Integer plannedAmount;
  @Column(name = "category", nullable = false)
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
    this.budgetDate = lineItem.getBudgetMonth().asLocalDate();
    this.name = lineItem.getName();
    this.plannedAmount = lineItem.getPlannedAmount().inCents();
    this.category = lineItem.getCategory();
    this.description = lineItem.getDescription();
  }

  public void setBudgetDate(LocalDate budgetDate) {
    this.budgetDate = budgetDate.withDayOfMonth(1);
  }

  public void setBudgetDate(BudgetMonth budgetMonth) {
    this.budgetDate = budgetMonth.asLocalDate();
  }

  public LineItem convertToLineItem() {
    return new LineItem(
        id,
        type,
        new BudgetMonth(budgetDate),
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
