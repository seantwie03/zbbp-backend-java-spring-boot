package me.seantwiehaus.zbbp.dao.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import me.seantwiehaus.zbbp.domain.BudgetMonth;
import me.seantwiehaus.zbbp.domain.Category;
import me.seantwiehaus.zbbp.domain.Money;

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
@NamedEntityGraph(name = "category.group.transactions", attributeNodes = {
        @NamedAttributeNode("transactionEntities"),
})
@Table(name = "categories")
public class CategoryEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    @Column(name = "planned_amount", nullable = false)
    private Integer plannedAmount;
    /**
     * BudgetDates only need the Year and Month; however, storing only the Year and Month in the database can be
     * tedious. Instead, the date is always set to the 1st.
     */
    @Column(name = "budget_date", nullable = false)
    private LocalDate budgetDate;
    @Column(name = "category_group_id", nullable = false)
    private Long categoryGroupId;
    @OneToMany
    @JoinColumn(name = "category_id")
    @OrderBy("date asc, amount asc")
    private List<TransactionEntity> transactionEntities = new ArrayList<>();

    public CategoryEntity(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.categoryGroupId = category.getCategoryGroupId();
        this.plannedAmount = category.getPlannedAmount().inCents();
        this.budgetDate = category.getBudgetMonth().asLocalDate();
        this.categoryGroupId = category.getCategoryGroupId();
    }

    public void setBudgetDate(LocalDate budgetDate) {
        this.budgetDate = budgetDate.withDayOfMonth(1);
    }

    public void setBudgetDate(BudgetMonth budgetMonth) {
        this.budgetDate = budgetMonth.asLocalDate();
    }

    public Category convertToCategory() {
        return new Category(
                lastModifiedAt,
                id,
                name,
                categoryGroupId,
                new Money(plannedAmount),
                new BudgetMonth(budgetDate),
                transactionEntities
                        .stream()
                        .map(TransactionEntity::convertToTransaction)
                        .toList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryEntity that = (CategoryEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
