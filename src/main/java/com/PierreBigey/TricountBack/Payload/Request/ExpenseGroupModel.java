package com.PierreBigey.TricountBack.Payload.Request;

import com.PierreBigey.TricountBack.Entity.ExpenseGroup;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Component
@NoArgsConstructor
public class ExpenseGroupModel {
    private String groupname;
    private String description;
    private List<Long> members_id;
    private List<Long> expenses_id;

    public ExpenseGroupModel(ExpenseGroup expenseGroup) {
        this.groupname = expenseGroup.getDescription();
        this.description = expenseGroup.getDescription();
        members_id = expenseGroup.getMembers_ids();
        expenses_id = expenseGroup.getExpenses_ids();
    }
}
