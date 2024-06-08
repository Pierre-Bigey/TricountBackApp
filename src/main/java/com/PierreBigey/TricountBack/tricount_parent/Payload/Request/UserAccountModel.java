package com.PierreBigey.TricountBack.tricount_parent.Payload.Request;

import com.PierreBigey.TricountBack.tricount_parent.Entity.UserAccount;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Component
@NoArgsConstructor
public class UserAccountModel {
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private List<Long> groups_ids;

    public UserAccountModel(UserAccount userAccount) {
        this.firstname = userAccount.getFirstname();
        this.lastname = userAccount.getLastname();
        this.username = userAccount.getUsername();
        this.password = userAccount.getPassword();
        this.groups_ids = userAccount.getGroups_ids();
    }
}
