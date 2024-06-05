package com.PierreBigey.TricountBack.Repository;

import com.PierreBigey.TricountBack.Entity.Expense;
import com.PierreBigey.TricountBack.Entity.ExpenseGroup;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    @Modifying
    @Transactional
    @Query(value = "UPDATE expense SET title = ?1, description = ?2, amount = ?3, author_id = ?4, group_id = ?5, WHERE id = ?6", nativeQuery = true)
    void updateById(String title,String description,int amount, long author_id, long group_id, long id);
}
