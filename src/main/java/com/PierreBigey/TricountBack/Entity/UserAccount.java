package com.PierreBigey.TricountBack.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

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

}
