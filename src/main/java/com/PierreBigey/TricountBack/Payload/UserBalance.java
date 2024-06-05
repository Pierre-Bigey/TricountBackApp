package com.PierreBigey.TricountBack.Payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBalance {
    private long userId;
    private double balance;
    private double totalExpense;

    public UserBalance(long userId){
        this.userId = userId;
        this.balance = 0;
        this.totalExpense = 0;
    }
}
