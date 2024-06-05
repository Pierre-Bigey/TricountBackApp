package com.PierreBigey.TricountBack.Payload;

import com.PierreBigey.TricountBack.Entity.UserAccount;
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
    private List<Long> groups_id;

    public UserAccountModel(UserAccount userAccount){
        this.firstname = userAccount.getFirstname();
        this.lastname = userAccount.getLastname();
        this.username = userAccount.getUsername();
        this.password = userAccount.getPassword();
        this.groups_id = userAccount.getExpenseGroups_ids();
    }
}
