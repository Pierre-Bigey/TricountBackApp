package com.PierreBigey.TricountBack.Repository;

import com.PierreBigey.TricountBack.Entity.UserAccount;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    @Modifying
    @Transactional
    @Query(value = "UPDATE user_account SET firstname = ?1, lastname = ?2 WHERE id = ?3", nativeQuery = true)
    void updateById(String firstname, String lastname, long id);
}
