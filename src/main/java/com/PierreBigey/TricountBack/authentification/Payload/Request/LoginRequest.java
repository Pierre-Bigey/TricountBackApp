package com.PierreBigey.TricountBack.authentification.Payload.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginRequest {

    private String username;
    private String password;

}
