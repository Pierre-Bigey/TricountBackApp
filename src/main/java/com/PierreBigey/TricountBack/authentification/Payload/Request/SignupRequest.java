package com.PierreBigey.TricountBack.authentification.Payload.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class SignupRequest {

    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private Set<String> role;

}
