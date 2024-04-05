package com.PierreBigey.TricountBack.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_account")
public class UserAccount extends BaseEntity {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(name = "firstname", nullable = false)
    @NotNull(message = "User must have a firstname.")
    private String firstname;

    @Column(name = "lastname", nullable = false)
    @NotNull(message = "User must have a lastname.")
    private String lastname;
}
