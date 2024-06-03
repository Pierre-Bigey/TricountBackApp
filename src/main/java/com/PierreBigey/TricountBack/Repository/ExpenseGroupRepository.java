package com.PierreBigey.TricountBack.Repository;

import com.PierreBigey.TricountBack.Entity.ExpenseGroup;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseGroupRepository extends JpaRepository<ExpenseGroup, Long> {
    @Modifying
    @Transactional
    @Query(value = "UPDATE expense_group SET groupname = ?1, description = ?2 WHERE id = ?3", nativeQuery = true)
    void updateById(String groupname,String description, long id);
}
