package me.seantwiehaus.zbbp.dao.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import me.seantwiehaus.zbbp.domain.CategoryGroup;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Data
@Table(name = "category_groups")
@NoArgsConstructor
public class CategoryGroupEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotNull
    @NotBlank
    private String name;
    @NotNull
    private LocalDate budgetDate;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "categoryGroupEntity")
    Set<CategoryEntity> categoryEntities;

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
