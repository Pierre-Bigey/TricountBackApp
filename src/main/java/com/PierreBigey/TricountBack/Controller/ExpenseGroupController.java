package com.PierreBigey.TricountBack.Controller;


import com.PierreBigey.TricountBack.Entity.ExpenseGroup;
import com.PierreBigey.TricountBack.Entity.UserAccount;
import com.PierreBigey.TricountBack.Payload.ExpenseGroupModel;
import com.PierreBigey.TricountBack.Payload.UserBalance;
import com.PierreBigey.TricountBack.Service.ExpenseGroupService;
import com.github.fge.jsonpatch.JsonPatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/groups")
public class ExpenseGroupController {

    @Autowired
    private ExpenseGroupService expenseGroupService;

    @PostMapping()
    public ResponseEntity<ExpenseGroup> createExpenseGroup(@RequestBody ExpenseGroupModel expenseGroupModel) {
        return new ResponseEntity<>(expenseGroupService.createExpenseGroup(expenseGroupModel), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseGroup> getOneExpenseGroup(@PathVariable("id") int id) {
        return new ResponseEntity<>(expenseGroupService.getExpenseGroupById((long) id), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<ExpenseGroup>> getAllExpenseGroups() {
        return new ResponseEntity<>(expenseGroupService.getAllExpenseGroups(), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateOneExpenseGroup(@PathVariable("id") int id, @RequestBody ExpenseGroupModel expenseGroupModel) {
            expenseGroupService.updateOne(id, expenseGroupModel);
    }

    @PatchMapping(value = "/update/{id}", consumes = "application/json-patch+json")
    public ResponseEntity<ExpenseGroup> patchOneExpenseGroup(@PathVariable("id") int id, @RequestBody JsonPatch patch) {
        return new ResponseEntity<>(expenseGroupService.patchOne(id, patch),HttpStatus.OK);
    }

    @PostMapping("/{groupId}/add-user")
    public ResponseEntity<ExpenseGroup> addUserToGroups(@PathVariable long groupId, @RequestBody List<Long> userIds) {
        return new ResponseEntity<>(expenseGroupService.addUsersToGroup(groupId, userIds),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOneExpenseGroup(@PathVariable("id") int id) {
        expenseGroupService.deleteById(id);
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllExpenseGroups() {
        expenseGroupService.deleteAll();
    }

    @GetMapping("/balance/{id}")
    public List<UserBalance> getGroupBalance(@PathVariable("id") int id){
        return expenseGroupService.getBalance(id);
    }

}
