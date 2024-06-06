package com.PierreBigey.TricountBack.tricount_parent.Repository;

import com.PierreBigey.TricountBack.tricount_parent.Entity.Expense;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    @Modifying
    @Transactional
    @Query(value = "UPDATE expense SET title = ?1, description = ?2, amount = ?3,expense_date = ?4, author_id = ?5, group_id = ?6, WHERE id = ?7", nativeQuery = true)
    void updateById(String title, String description, double amount, Date expense_date, long author_id, long group_id, long id);
}
