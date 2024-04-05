package com.PierreBigey.TricountBack.Payload;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class UserAccountModel {
    private String firstname;
    private String lastname;
    private String username;
    private String password;
}
