package com.PierreBigey.TricountBack.Payload;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class ExpenseGroupModel {
    private String groupname;
    private String description;

}
