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
@Table(name = "expense_group")
public class ExpenseGroup extends BaseEntity {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(name = "groupname", nullable = false)
    @NotNull(message = "Group must have a name")
    private String groupname;

    @Column(name = "description", nullable = false)
    @NotNull(message = "group must have a description.")
    private String description;

}
