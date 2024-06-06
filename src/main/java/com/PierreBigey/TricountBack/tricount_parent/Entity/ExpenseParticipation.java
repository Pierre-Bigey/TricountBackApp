package com.PierreBigey.TricountBack.tricount_parent.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "expense_participation")
public class ExpenseParticipation extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "expense_participation_id_seq")
    @SequenceGenerator(name = "expense_participation_id_seq", sequenceName = "expense_participation_id_seq", allocationSize = 1)
    @Column(nullable = false)
    private long id;

    @ManyToOne
    @JoinColumn(
            name = "expense_id",
            referencedColumnName = "id"
    )
    @NotNull(message = "Expense participation must be linked to an expense")
    @JsonIgnore
    private Expense expense;

    public long getExpense_id() {
        return expense.getId();
    }

    @ManyToOne
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id"
    )
    @NotNull(message = "Expense participation must be linked to a user")
    @JsonIgnore
    private UserAccount user;

    public long getUser_id() {
        return user.getId();
    }

    @Column(name = "weight", nullable = false)
    private int weight;
}
