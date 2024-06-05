package com.PierreBigey.TricountBack.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "expense")
public class Expense extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "expense_id_seq")
    @SequenceGenerator(name = "expense_id_seq", sequenceName = "expense_id_seq", allocationSize = 1)
    @Column(nullable = false)
    private long id;

    @Column(name = "title", nullable = false)
    @NotNull(message = "Expense must have a title.")
    private String title;

    @Column(name = "description", nullable = true)
    private String description;

    @Column(name = "amount", nullable = false)
    @NotNull(message = "Expense must have a amount.")
    private int amount;

    @ManyToOne
    @JoinColumn(
            name = "author_id",
            referencedColumnName = "id" // primary key of the user who owns this MESSAGE
    )
    @NotNull(message = "Expense must have an author")
    private UserAccount author;

    @ManyToOne
    @JoinColumn(
            name = "group_id",
            referencedColumnName = "id" // primary key of the user who owns this MESSAGE
    )
    @NotNull(message = "Expense must have a group.")
    private ExpenseGroup group;

}
