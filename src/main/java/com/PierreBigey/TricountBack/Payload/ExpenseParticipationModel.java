package com.PierreBigey.TricountBack.Payload;

import com.PierreBigey.TricountBack.Entity.ExpenseParticipation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Getter
@Component
@NoArgsConstructor
public class ExpenseParticipationModel {
    private long expense_id;
    private long user_id;
    private int weight;

    public ExpenseParticipationModel(ExpenseParticipation expenseParticipation){
        this.expense_id = expenseParticipation.getExpense_id();
        this.user_id = expenseParticipation.getUser_id();
        this.weight = expenseParticipation.getWeight();
    }
}
