package me.seantwiehaus.zbbp.dao.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import me.seantwiehaus.zbbp.domain.Transaction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "transactions")
@NoArgsConstructor
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotNull
    private BigDecimal amount;
    @NotNull
    private LocalDate date;
    @NotNull
    @NotBlank
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "categories_transactions",
            joinColumns = @JoinColumn(name = "transaction_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private CategoryEntity categoryEntity;

    public Transaction convertToTransaction() {
        return new Transaction(
                id,
                amount,
                date,
                description
        );
    }
}
