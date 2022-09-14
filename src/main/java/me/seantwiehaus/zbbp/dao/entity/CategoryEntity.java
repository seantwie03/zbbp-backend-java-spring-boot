package me.seantwiehaus.zbbp.dao.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.seantwiehaus.zbbp.domain.BudgetMonth;
import me.seantwiehaus.zbbp.domain.Category;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "categories")
@NamedEntityGraph(name = "category.group.transactions", attributeNodes = {
        @NamedAttributeNode("categoryGroupEntity"),
        @NamedAttributeNode("transactionEntities"),
})
public class CategoryEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private BigDecimal plannedAmount;
    /**
     * BudgetDates only need the Year and Month; however, storing only the Year and Month in the database can be
     * tedious. Instead, the date is always set to the 1st.
     */
    @NotNull
    private LocalDate budgetDate;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_group_id")
    private CategoryGroupEntity categoryGroupEntity;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "categoryEntity")
    private Set<TransactionEntity> transactionEntities;

    public CategoryEntity(Long id,
                          String name,
                          BigDecimal plannedAmount,
                          LocalDate budgetDate,
                          CategoryGroupEntity categoryGroupEntity,
                          Set<TransactionEntity> transactionEntities) {
        this.id = id;
        this.name = name;
        this.plannedAmount = plannedAmount;
        setBudgetDate(budgetDate);
        this.categoryGroupEntity = categoryGroupEntity;
        this.transactionEntities = transactionEntities;
    }

    public void setBudgetDate(LocalDate budgetDate) {
        this.budgetDate = budgetDate.withDayOfMonth(1);
    }

    public Category convertToCategory() {
        return new Category(
                lastModifiedAt,
                id,
                name,
                plannedAmount,
                new BudgetMonth(budgetDate),
                transactionEntities
                        .stream()
                        .map(TransactionEntity::convertToTransaction)
                        .toList()
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryEntity that = (CategoryEntity) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
