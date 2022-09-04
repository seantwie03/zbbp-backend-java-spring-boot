package me.seantwiehaus.zbbp.dao.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import me.seantwiehaus.zbbp.domain.Category;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Data
@Table(name = "categories")
@NoArgsConstructor
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private BigDecimal amount;
    @NotNull
    private LocalDate budgetDate;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_group_id", referencedColumnName = "id")
    private CategoryGroupEntity categoryGroupEntity;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "categoryEntity")
    private Set<TransactionEntity> transactionEntities;

    public Category convertToCategory() {
        return new Category(
                id,
                name,
                amount,
                budgetDate,
                categoryGroupEntity.convertToCategoryGroup(),
                transactionEntities.stream()
                        .map(TransactionEntity::convertToTransaction)
                        .toList()
        );
    }
}
