package com.PierreBigey.TricountBack.Payload;

import com.PierreBigey.TricountBack.Entity.ExpenseGroup;
import com.PierreBigey.TricountBack.Entity.UserAccount;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class ExpenseModel {
    private String title;
    private String description;
    private int amount;
    private long author;
    private long group;

}
