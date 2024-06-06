package com.PierreBigey.TricountBack.tricount_parent.Payload.Response;

import com.PierreBigey.TricountBack.tricount_parent.Utils.CustomNumberFormat;
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

    public UserBalance(long userId) {
        this.userId = userId;
        this.balance = 0;
        this.totalExpense = 0;
    }

    public void setBalance(double value) {
        balance = CustomNumberFormat.format(value);
    }

    public void setTotalExpense(double value) {
        totalExpense = CustomNumberFormat.format(value);
    }

}
