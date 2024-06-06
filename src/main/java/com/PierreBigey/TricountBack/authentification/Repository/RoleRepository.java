package com.PierreBigey.TricountBack.authentification.Repository;

import com.PierreBigey.TricountBack.authentification.Model.ERole;
import com.PierreBigey.TricountBack.authentification.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
