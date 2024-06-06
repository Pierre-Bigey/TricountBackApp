package com.PierreBigey.TricountBack.Service;

import com.PierreBigey.TricountBack.Entity.Expense;
import com.PierreBigey.TricountBack.Entity.ExpenseParticipation;
import com.PierreBigey.TricountBack.Entity.UserAccount;
import com.PierreBigey.TricountBack.Exception.ResourceNotFoundException;
import com.PierreBigey.TricountBack.Exception.UserNotInGroupException;
import com.PierreBigey.TricountBack.Payload.Request.ExpenseParticipationModel;
import com.PierreBigey.TricountBack.Repository.ExpenseParticipationRepository;
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
public class ExpenseParticipationService {

    @Autowired
    private ExpenseParticipationRepository expenseParticipationRepository;

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private ExpenseService expenseService;


    //Create a new participation
    public ExpenseParticipation createExpenseParticipation(ExpenseParticipationModel expenseParticipationModel) {
        UserAccount userAccount = userAccountService.getUserAccountById(expenseParticipationModel.getUser_id());
        Expense expense = expenseService.getExpenseById(expenseParticipationModel.getExpense_id());

        if (!userAccount.getGroups_ids().contains(expense.getGroup_id())) {
            throw new UserNotInGroupException(String.format("User's ID (%d) not found in group ID (%d) where expense ID (%d) belongs", userAccount.getId(), expense.getGroup_id(),expense.getId()));
        }

        ExpenseParticipation expenseParticipationToSave = ExpenseParticipation.builder()
                .expense(expense)
                .user(userAccount)
                .weight(expenseParticipationModel.getWeight())
                .build();
        return expenseParticipationRepository.save(expenseParticipationToSave);
    }

    //Get all participations
    public List<ExpenseParticipation> getAllExpenseParticipations() {
        return expenseParticipationRepository.findAll().stream().sorted(Comparator.comparing(ExpenseParticipation::getId)).toList();
    }

    //Get a participation by id
    public ExpenseParticipation getExpenseParticipationById(Long id) {
        return expenseParticipationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Expense participation with ID " + id + " not found"));
    }

    //Update a participation
    public void updateOne(long id, ExpenseParticipationModel expenseParticipationModel) {
        if (expenseParticipationRepository.findById(id).isEmpty()) throw new ResourceNotFoundException("Expense participation with ID " + id + " not found");
        expenseParticipationRepository.updateById(expenseParticipationModel.getExpense_id(), expenseParticipationModel.getUser_id(),expenseParticipationModel.getWeight(), id);
    }

    public ExpenseParticipation patchOne(long id, JsonPatch patch) {
        var expenseParticipation = expenseParticipationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Expense participation with ID " + id + " not found"));
        var expenseParticipationPatched = applyPatchToExpenseParticipation(patch, expenseParticipation);
        return expenseParticipationRepository.save(expenseParticipationPatched);
    }

    public void deleteById(long id) {
        expenseParticipationRepository.deleteById(id);
    }

    public void deleteAll() {
        expenseParticipationRepository.deleteAll();
    }

    private ExpenseParticipation applyPatchToExpenseParticipation(JsonPatch patch, ExpenseParticipation expenseParticipation) {
        try {
            var objectMapper = new ObjectMapper();
            JsonNode patched = patch.apply(objectMapper.convertValue(expenseParticipation, JsonNode.class));
            return objectMapper.treeToValue(patched, ExpenseParticipation.class);
        } catch (JsonPatchException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
