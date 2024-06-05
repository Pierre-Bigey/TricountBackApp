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
    private List<UserAccount> members;

}
