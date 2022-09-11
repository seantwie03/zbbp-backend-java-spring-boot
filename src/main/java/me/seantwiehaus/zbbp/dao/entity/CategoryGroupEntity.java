package me.seantwiehaus.zbbp.dao.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import me.seantwiehaus.zbbp.domain.BudgetDate;
import me.seantwiehaus.zbbp.domain.CategoryGroup;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Table(name = "category_groups")
@NoArgsConstructor
@NamedEntityGraph(name = "group.categories.transactions", attributeNodes = {
        @NamedAttributeNode(value = "categoryEntities", subgraph = "transactions"),
}, subgraphs = {
        @NamedSubgraph(name = "transactions", attributeNodes = {
                @NamedAttributeNode("transactionEntities")
        })
})
public class CategoryGroupEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotNull
    @NotBlank
    private String name;
    /**
     * BudgetDates only need the Year and Month; however, storing only the Year and Month in the database can be
     * tedious. Instead, the date is always set to the 1st.
     */
    @NotNull
    private LocalDate budgetDate;
    @OneToMany(mappedBy = "categoryGroupEntity")
    private List<CategoryEntity> categoryEntities;

    public CategoryGroupEntity(Long id,
                               String name,
                               LocalDate budgetDate,
                               List<CategoryEntity> categoryEntities) {
        this.id = id;
        this.name = name;
        setBudgetDate(budgetDate);
        this.categoryEntities = categoryEntities;
    }

    public void setBudgetDate(LocalDate budgetDate) {
        this.budgetDate = budgetDate.withDayOfMonth(1);
    }

    public void addCategoryEntity(CategoryEntity categoryEntity) {
        categoryEntities.add(categoryEntity);
        categoryEntity.setCategoryGroupEntity(this);
    }

    public void removeCategoryEntity(CategoryEntity categoryEntity) {
        categoryEntities.remove(categoryEntity);
        categoryEntity.setCategoryGroupEntity(null);
    }

    public CategoryGroup convertToCategoryGroup() {
        return new CategoryGroup(
                id,
                name,
                BudgetDate.from(budgetDate),
                categoryEntities.stream()
                        .map(CategoryEntity::convertToCategory)
                        .toList()
        );
    }
}
