package me.seantwiehaus.zbbp.dao.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
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
    @NotNull
    private LocalDate budgetDate;
    @OneToMany(mappedBy = "categoryGroupEntity")
    private List<CategoryEntity> categoryEntities;

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
                budgetDate,
                categoryEntities.stream()
                        .map(CategoryEntity::convertToCategory)
                        .toList()
        );
    }
}
