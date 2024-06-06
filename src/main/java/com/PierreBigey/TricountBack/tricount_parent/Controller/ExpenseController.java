package com.PierreBigey.TricountBack.tricount_parent.Controller;


import com.PierreBigey.TricountBack.tricount_parent.Entity.Expense;
import com.PierreBigey.TricountBack.tricount_parent.Payload.Request.ExpenseModel;
import com.PierreBigey.TricountBack.tricount_parent.Service.ExpenseService;
import com.github.fge.jsonpatch.JsonPatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @PostMapping()
    public ResponseEntity<Expense> createExpense(@RequestBody ExpenseModel expenseModel) {
        return new ResponseEntity<>(expenseService.createExpense(expenseModel), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Expense> getOneExpense(@PathVariable("id") int id) {
        return new ResponseEntity<>(expenseService.getExpenseById((long) id), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<Expense>> getAllExpenses() {
        return new ResponseEntity<>(expenseService.getAllExpenses(), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateOneExpense(@PathVariable("id") int id, @RequestBody ExpenseModel expenseModel) {
            expenseService.updateOne(id, expenseModel);
    }

    @PatchMapping(value = "/update/{id}", consumes = "application/json-patch+json")
    public ResponseEntity<Expense> patchOneExpense(@PathVariable("id") int id, @RequestBody JsonPatch patch) {
        return new ResponseEntity<>(expenseService.patchOne(id, patch),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOneExpense(@PathVariable("id") int id) {
        expenseService.deleteById(id);
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllExpenses() {
        expenseService.deleteAll();
    }

}
