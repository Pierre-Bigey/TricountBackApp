package com.PierreBigey.TricountBack.tricount_parent.Controller;


import com.PierreBigey.TricountBack.tricount_parent.Entity.ExpenseParticipation;
import com.PierreBigey.TricountBack.tricount_parent.Payload.Request.ExpenseParticipationModel;
import com.PierreBigey.TricountBack.tricount_parent.Service.ExpenseParticipationService;
import com.github.fge.jsonpatch.JsonPatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RequestMapping("/api/participations")
public class ExpenseParticipationController {

    @Autowired
    private ExpenseParticipationService expenseParticipationService;

    @PostMapping()
    public ResponseEntity<ExpenseParticipation> createExpenseParticipation(@RequestBody ExpenseParticipationModel expenseParticipationModel) {
        return new ResponseEntity<>(expenseParticipationService.createExpenseParticipation(expenseParticipationModel), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseParticipation> getOneExpenseParticipation(@PathVariable("id") int id) {
        return new ResponseEntity<>(expenseParticipationService.getExpenseParticipationById((long) id), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<ExpenseParticipation>> getAllExpenseParticipations() {
        return new ResponseEntity<>(expenseParticipationService.getAllExpenseParticipations(), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateOneExpenseParticipation(@PathVariable("id") int id, @RequestBody ExpenseParticipationModel expenseParticipationModel) {
            expenseParticipationService.updateOne(id, expenseParticipationModel);
    }

    @PatchMapping(value = "/update/{id}", consumes = "application/json-patch+json")
    public ResponseEntity<ExpenseParticipation> patchOneExpenseParticipation(@PathVariable("id") int id, @RequestBody JsonPatch patch) {
        return new ResponseEntity<>(expenseParticipationService.patchOne(id, patch),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOneExpenseParticipation(@PathVariable("id") int id) {
        expenseParticipationService.deleteById(id);
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllExpenseParticipations() {
        expenseParticipationService.deleteAll();
    }

}
