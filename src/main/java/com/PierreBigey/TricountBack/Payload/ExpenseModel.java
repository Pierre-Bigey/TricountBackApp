package com.PierreBigey.TricountBack.Payload;

import com.PierreBigey.TricountBack.Entity.Expense;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Component
@NoArgsConstructor
public class ExpenseModel {
    private String title;
    private String description;
    private double amount;
    private long author_id;
    private long group_id;
    private List<Long> participations_ids;

    public ExpenseModel(Expense expense) {
        this.title = expense.getTitle();
        this.description = expense.getDescription();
        this.amount = expense.getAmount();
        this.author_id = expense.getAuthor_id();
        this.group_id = expense.getGroup_id();
        this.participations_ids = expense.getParticipations_ids();
    }
}
