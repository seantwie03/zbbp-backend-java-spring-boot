package me.seantwiehaus.zbbp.dao.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.seantwiehaus.zbbp.domain.Transaction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "transactions")
public class TransactionEntity extends BaseEntity {
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

    public TransactionEntity(Transaction transaction) {
        this.id = transaction.getId();
        this.amount = transaction.getAmount();
        this.date = transaction.getDate();
        this.description = transaction.getDescription();
    }

    public Transaction convertToTransaction() {
        return new Transaction(lastModifiedAt,
                id,
                amount,
                date,
                description
        );
    }
}
