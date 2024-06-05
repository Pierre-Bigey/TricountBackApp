package com.PierreBigey.TricountBack.Entity;

import com.PierreBigey.TricountBack.Payload.ExpenseGroupModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "expense_group")
public class ExpenseGroup extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "expense_group_id_seq")
    @SequenceGenerator(name = "expense_group_id_seq", sequenceName = "expense_group_id_seq", allocationSize = 1)
    @Column(nullable = false)
    private long id;

    @Column(name = "groupname", nullable = false)
    @NotNull(message = "Group must have a name")
    private String groupname;

    @Column(name = "description", nullable = false)
    @NotNull(message = "group must have a description.")
    private String description;

    @ManyToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER // fetches the child entities along with parent (not when required)
    )
    @JoinTable(
            name = "users_groups",
            joinColumns = @JoinColumn(
                    name = "group_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "user_id",
                    referencedColumnName = "id"
            )
    )
    @JsonIgnore
    private List<UserAccount> members;

    public List<Long> getMembers_ids(){
        if(Objects.isNull(members)){
            return new ArrayList<>();
        }
        return members.stream()
                .map(UserAccount::getId)
                .collect(Collectors.toList());
    }

    @OneToMany
    @JoinColumn(name = "group_id")
    @JsonIgnore
    private List<Expense> expenses;

    public List<Long> getExpenses_ids(){
        if(Objects.isNull(expenses)){
            return new ArrayList<>();
        }
        return expenses.stream()
                .map(Expense::getId)
                .collect(Collectors.toList());
    }

    public ExpenseGroupModel viewAsExpenseGroupModel(){
        return new ExpenseGroupModel(this);
    }

}
