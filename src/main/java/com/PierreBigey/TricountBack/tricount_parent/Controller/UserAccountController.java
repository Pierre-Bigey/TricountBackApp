package com.PierreBigey.TricountBack.tricount_parent.Controller;


import com.PierreBigey.TricountBack.tricount_parent.Entity.UserAccount;
import com.PierreBigey.TricountBack.tricount_parent.Payload.Request.UserAccountModel;
import com.PierreBigey.TricountBack.tricount_parent.Service.UserAccountService;
import com.github.fge.jsonpatch.JsonPatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/users")
public class UserAccountController {

    @Autowired
    private UserAccountService userAccountService;

    @PostMapping()
    public ResponseEntity<UserAccount> createUserAccount(@RequestBody UserAccountModel userAccountModel) {
        return new ResponseEntity<>(userAccountService.createUserAccount(userAccountModel), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserAccount> getOneUserAccount(@PathVariable("id") int id) {
        return new ResponseEntity<>(userAccountService.getUserAccountById((long) id), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<UserAccount>> getAllUserAccounts() {
        return new ResponseEntity<>(userAccountService.getAllUserAccounts(), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateOneUserAccount(@PathVariable("id") int id, @RequestBody UserAccountModel userAccountModel) {
            userAccountService.updateOne(id, userAccountModel);
    }

    @PatchMapping(value = "/update/{id}", consumes = "application/json-patch+json")
    public ResponseEntity<UserAccount> patchOneUserAccount(@PathVariable("id") int id, @RequestBody JsonPatch patch) {
        return new ResponseEntity<>(userAccountService.patchOne(id, patch),HttpStatus.OK);
    }

    @PostMapping("/{userId}/add-group")
    public ResponseEntity<UserAccount> addUserToGroups(@PathVariable long userId, @RequestBody List<Long> groupIds) {
        return new ResponseEntity<>(userAccountService.addGroupsToUser(userId, groupIds),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOneUserAccount(@PathVariable("id") int id) {
        userAccountService.deleteById(id);
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllUserAccounts() {
        userAccountService.deleteAll();
    }

}
