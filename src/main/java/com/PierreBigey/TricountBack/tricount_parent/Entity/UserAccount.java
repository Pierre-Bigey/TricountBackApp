package com.PierreBigey.TricountBack.tricount_parent.Entity;

import com.PierreBigey.TricountBack.authentification.Model.Role;
import com.PierreBigey.TricountBack.tricount_parent.Payload.Request.UserAccountModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_account")
public class UserAccount extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_account_id_seq")
    @SequenceGenerator(name = "user_account_id_seq", sequenceName = "user_account_id_seq", allocationSize = 1)
    @Column(nullable = false)
    private long id;

    @Column(name = "username", nullable = false)
    @NotNull(message = "User must have a username")
    private String username;

    @Column(name = "firstname", nullable = false)
    @NotNull(message = "User must have a firstname.")
    private String firstname;

    @Column(name = "lastname", nullable = false)
    @NotNull(message = "User must have a lastname.")
    private String lastname;

    @Column(name = "password", nullable = false)
    @NotNull(message = "User must have a password.")
    private String password;


    @ManyToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER // fetches the child entities along with parent (not when required)
    )
    @JoinTable(
            name = "users_groups",
            joinColumns = @JoinColumn(
                    name = "user_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "group_id",
                    referencedColumnName = "id"
            )
    )
    @JsonIgnore
    private Collection<ExpenseGroup> expenseGroups;

    public List<Long> getGroups_ids() {
        if (Objects.isNull(expenseGroups)) {
            return new ArrayList<>();
        }
        return expenseGroups.stream()
                .map(ExpenseGroup::getId)
                .collect(Collectors.toList());
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();


    public UserAccount(String _username, String _firstname, String _lastname, String _password){
        this.username = _username;
        this.firstname = _firstname;
        this.lastname = _lastname;
        this.password = _password;
    }


    public UserAccountModel viewAsUserAccountModel() {
        return new UserAccountModel(this);
    }
}
