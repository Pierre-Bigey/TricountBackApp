package com.PierreBigey.TricountBack.Service;

import com.PierreBigey.TricountBack.Entity.Expense;
import com.PierreBigey.TricountBack.Exception.ResourceNotFoundException;
import com.PierreBigey.TricountBack.Payload.Request.ExpenseModel;
import com.PierreBigey.TricountBack.Repository.ExpenseRepository;
import com.PierreBigey.TricountBack.Utils.CustomNumberFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
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


    //Create a new expense
    public Expense createExpense(ExpenseModel expenseModel) {
        Expense expenseToSave = Expense.builder()
                .title(expenseModel.getTitle())
                .description(expenseModel.getDescription())
                .amount(CustomNumberFormat.format(expenseModel.getAmount()))
                .expense_date(expenseModel.getExpense_date())
                .author(userAccountService.getUserAccountById(expenseModel.getAuthor_id()))
                .group(expenseGroupService.getExpenseGroupById(expenseModel.getGroup_id()))
                .build();
        return expenseRepository.save(expenseToSave);
    }

    //Get all expenses
    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll().stream().sorted(Comparator.comparing(Expense::getId)).toList();
    }

    //Get an expense by id
    public Expense getExpenseById(Long id) {
        return expenseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Expense with ID " + id + " not found"));
    }

    //Update an expense
    public void updateOne(long id, ExpenseModel expenseModel) {
        if (expenseRepository.findById(id).isEmpty()) throw new ResourceNotFoundException("Expense with ID " + id + " not found");
        expenseRepository.updateById(expenseModel.getTitle(), expenseModel.getDescription(),expenseModel.getAmount(),expenseModel.getExpense_date(), expenseModel.getAuthor_id(),expenseModel.getGroup_id(), id);
    }

    public Expense patchOne(long id, JsonPatch patch) {
        var expense = expenseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Expense with ID " + id + " not found"));
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
