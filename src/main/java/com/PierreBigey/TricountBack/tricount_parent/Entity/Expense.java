package com.PierreBigey.TricountBack.tricount_parent.Entity;

import com.PierreBigey.TricountBack.tricount_parent.Payload.Request.ExpenseModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    @Column(name = "description")
    private String description;

    @Column(name = "amount", nullable = false)
    @NotNull(message = "Expense must have a amount.")
    private double amount;

    @Column(name = "expense_date", nullable = false)
    @NotNull(message = "Expense must have an expense_date")
    private Date expense_date;

    @ManyToOne
    @JoinColumn(
            name = "author_id",
            referencedColumnName = "id"
    )
    @NotNull(message = "Expense must have an author")
    @JsonIgnore
    private UserAccount author;

    public long getAuthor_id() {
        return author.getId();
    }

    @ManyToOne
    @JoinColumn(
            name = "group_id",
            referencedColumnName = "id"
    )
    @NotNull(message = "Expense must have a group.")
    @JsonIgnore
    private ExpenseGroup group;

    public long getGroup_id() {
        return group.getId();
    }

    @OneToMany
    @JoinColumn(name = "expense_id")
    @JsonIgnore
    private List<ExpenseParticipation> participations;

    public List<Long> getParticipations_ids() {
        if (Objects.isNull(participations)) {
            return new ArrayList<>();
        }
        return participations.stream()
                .map(ExpenseParticipation::getId)
                .collect(Collectors.toList());
    }

    public int getSumOfWeight() {
        int sum = 0;
        if (!Objects.isNull(participations)) {
            for(ExpenseParticipation participation : participations) {
                sum += participation.getWeight();
            }
        }
        return sum;
    }

    public ExpenseModel viewAsExpenseModel() {
        return new ExpenseModel(this);
    }
}
