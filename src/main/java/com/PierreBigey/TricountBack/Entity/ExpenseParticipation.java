package com.PierreBigey.TricountBack.Entity;

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

    @Column(name = "expense_id", nullable = false)
    private long expense_id;

    @ManyToOne
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id"
    )
    @NotNull(message = "Expense participation must be linked to a user")
    private UserAccount user;

    @Column(name = "weight", nullable = false)
    private int weight;
}
