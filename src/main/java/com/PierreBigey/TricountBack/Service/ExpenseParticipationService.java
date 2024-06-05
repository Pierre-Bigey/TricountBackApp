package com.PierreBigey.TricountBack.Service;

import com.PierreBigey.TricountBack.Entity.ExpenseParticipation;
import com.PierreBigey.TricountBack.Payload.ExpenseParticipationModel;
import com.PierreBigey.TricountBack.Repository.ExpenseParticipationRepository;
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
public class ExpenseParticipationService {

    @Autowired
    private ExpenseParticipationRepository expenseParticipationRepository;

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private ExpenseService expenseService;


    //Create a new participation
    public ExpenseParticipation createExpenseParticipation(ExpenseParticipationModel expenseParticipationModel){
        ExpenseParticipation expenseParticipationToSave = ExpenseParticipation.builder()
                .expense(expenseService.getExpenseById(expenseParticipationModel.getExpense_id()))
                .user(userAccountService.getUserAccountById(expenseParticipationModel.getUser_id()))
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
        return expenseParticipationRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    //Update a participation
    public void updateOne(long id, ExpenseParticipationModel expenseParticipationModel) {
        if (expenseParticipationRepository.findById(id).isEmpty()) throw new EntityNotFoundException();
        expenseParticipationRepository.updateById(expenseParticipationModel.getExpense_id(), expenseParticipationModel.getUser_id(),expenseParticipationModel.getWeight(), id);
    }

    public ExpenseParticipation patchOne(long id, JsonPatch patch) {
        var expenseParticipation = expenseParticipationRepository.findById(id).orElseThrow(EntityNotFoundException::new);
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
