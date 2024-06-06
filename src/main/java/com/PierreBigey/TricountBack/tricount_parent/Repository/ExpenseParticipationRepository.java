package com.PierreBigey.TricountBack.tricount_parent.Repository;

import com.PierreBigey.TricountBack.tricount_parent.Entity.ExpenseParticipation;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseParticipationRepository extends JpaRepository<ExpenseParticipation, Long> {
    @Modifying
    @Transactional
    @Query(value = "UPDATE expense_participation SET expense_id = ?1, user_id = ?2, weight = ?3, WHERE id = ?4", nativeQuery = true)
    void updateById(long expense_id, long user_id, int weight, long id);
}
