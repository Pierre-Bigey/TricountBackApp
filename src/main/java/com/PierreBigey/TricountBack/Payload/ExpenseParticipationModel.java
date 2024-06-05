package com.PierreBigey.TricountBack.Payload;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class ExpenseParticipationModel {
    private long expense;
    private long user;
    private int weight;
}
