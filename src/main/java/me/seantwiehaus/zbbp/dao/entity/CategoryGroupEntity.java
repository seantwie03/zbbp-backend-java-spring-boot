package me.seantwiehaus.zbbp.dao.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import me.seantwiehaus.zbbp.domain.BudgetMonth;
import me.seantwiehaus.zbbp.domain.CategoryGroup;

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
@NamedEntityGraph(name = "group.lineItems.transactions", attributeNodes = {
    @NamedAttributeNode(value = "lineItemEntities", subgraph = "transactions"),
}, subgraphs = {
    @NamedSubgraph(name = "transactions", attributeNodes = {
        @NamedAttributeNode("transactionEntities")
    })
})
@Table(name = "category_groups", uniqueConstraints = { @UniqueConstraint(columnNames = { "name", "budget_date" }) })
public class CategoryGroupEntity extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;
  @Column(name = "name", nullable = false)
  private String name;
  /**
   * BudgetDates only need the Year and Month; however, storing only the Year and Month in the database can be
   * tedious. Instead, the date is always set to the 1st.
   */
  @Column(name = "budget_date", nullable = false)
  private LocalDate budgetDate;
  @OneToMany
  @JoinColumn(name = "category_group_id")
  private List<LineItemEntity> lineItemEntities = new ArrayList<>();

  public void setBudgetDate(LocalDate budgetDate) {
    this.budgetDate = budgetDate.withDayOfMonth(1);
  }

  public void setBudgetDate(BudgetMonth budgetMonth) {
    this.budgetDate = budgetMonth.asLocalDate();
  }

  public CategoryGroup convertToCategoryGroup() {
    return new CategoryGroup(
        lastModifiedAt,
        id,
        name,
        new BudgetMonth(budgetDate),
        lineItemEntities
            .stream()
            .map(LineItemEntity::convertToLineItem)
            .toList());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CategoryGroupEntity that = (CategoryGroupEntity) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
