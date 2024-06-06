package com.PierreBigey.TricountBack.tricount_parent.Repository;

import com.PierreBigey.TricountBack.tricount_parent.Entity.UserAccount;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    @Modifying
    @Transactional
    @Query(value = "UPDATE user_account SET username = ?1, firstname = ?2, lastname = ?3, password = ?4 WHERE id = ?5", nativeQuery = true)
    void updateById(String username,String firstname, String lastname, String password, long id);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO users_groups (user_id, group_id) VALUES (?1,?2);", nativeQuery = true)
    void AddGroupToUser(long user_id, long group_id);

}
