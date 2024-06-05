package com.PierreBigey.TricountBack.Service;

import com.PierreBigey.TricountBack.Entity.Expense;
import com.PierreBigey.TricountBack.Payload.ExpenseModel;
import com.PierreBigey.TricountBack.Repository.ExpenseRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private ExpenseGroupService expenseGroupService;

    //Create a new group
    public Expense createExpense(ExpenseModel expenseModel){
        Expense expenseToSave = Expense.builder()
                .title(expenseModel.getTitle())
                .description(expenseModel.getDescription())
                .amount(expenseModel.getAmount())
                .author(userAccountService.getUserAccountById(expenseModel.getAuthor_id()))
                .group(expenseGroupService.getExpenseGroupById(expenseModel.getGroup_id()))
                .build();
        return expenseRepository.save(expenseToSave);
    }

    //Get all user accounts
    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll().stream().sorted(Comparator.comparing(Expense::getId)).toList();
    }

    //Get a user account by id
    public Expense getExpenseById(Long id) {
        return expenseRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    //Update a user account
    public void updateOne(long id, ExpenseModel expenseModel) {
        if (expenseRepository.findById(id).isEmpty()) throw new EntityNotFoundException();
        expenseRepository.updateById(expenseModel.getTitle(), expenseModel.getDescription(),expenseModel.getAmount(),expenseModel.getAuthor_id(),expenseModel.getGroup_id(), id);
    }

    public Expense patchOne(long id, JsonPatch patch) {
        var expense = expenseRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        var expensePatched = applyPatchToExpense(patch, expense);
        return expenseRepository.save(expensePatched);
    }

    public void deleteById(long id) {
        expenseRepository.deleteById(id);
    }

    public void deleteAll() {
        expenseRepository.deleteAll();
    }

    private Expense applyPatchToExpense(JsonPatch patch, Expense expense) {
        try {
            var objectMapper = new ObjectMapper();
            JsonNode patched = patch.apply(objectMapper.convertValue(expense, JsonNode.class));
            return objectMapper.treeToValue(patched, Expense.class);
        } catch (JsonPatchException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
