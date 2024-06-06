package com.PierreBigey.TricountBack.authentification.Payload.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class UserInfoResponse {
    private long id;
    private String username;
    private String firstname;
    private String lastname;
    private List<String> roles;
}
