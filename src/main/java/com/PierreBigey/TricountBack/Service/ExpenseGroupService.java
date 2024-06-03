package com.PierreBigey.TricountBack.Service;

import com.PierreBigey.TricountBack.Entity.ExpenseGroup;
import com.PierreBigey.TricountBack.Payload.ExpenseGroupModel;
import com.PierreBigey.TricountBack.Repository.ExpenseGroupRepository;
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
public class ExpenseGroupService {

    @Autowired
    private ExpenseGroupRepository expenseGroupRepository;

    //Create a new group
    public ExpenseGroup createExpenseGroup(ExpenseGroupModel expenseGroupModel){
        ExpenseGroup expenseGroupToSave = ExpenseGroup.builder()
                .groupname(expenseGroupModel.getGroupname())
                .description(expenseGroupModel.getDescription())
                .build();
        return expenseGroupRepository.save(expenseGroupToSave);
    }

    //Get all user accounts
    public List<ExpenseGroup> getAllExpenseGroups() {
        return expenseGroupRepository.findAll().stream().sorted(Comparator.comparing(ExpenseGroup::getId)).toList();
    }

    //Get a user account by id
    public ExpenseGroup getExpenseGroupById(Long id) {
        return expenseGroupRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    //Update a user account
    public void updateOne(long id, ExpenseGroupModel expenseGroupModel) {
        if (expenseGroupRepository.findById(id).isEmpty()) throw new EntityNotFoundException();
        expenseGroupRepository.updateById(expenseGroupModel.getGroupname(), expenseGroupModel.getDescription(), id);
    }

    public ExpenseGroup patchOne(long id, JsonPatch patch) {
        var expenseGroup = expenseGroupRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        var expenseGroupPatched = applyPatchToExpenseGroup(patch, expenseGroup);
        return expenseGroupRepository.save(expenseGroupPatched);
    }

    public void deleteById(long id) {
        expenseGroupRepository.deleteById(id);
    }

    public void deleteAll() {
        expenseGroupRepository.deleteAll();
    }

    private ExpenseGroup applyPatchToExpenseGroup(JsonPatch patch, ExpenseGroup expenseGroup) {
        try {
            var objectMapper = new ObjectMapper();
            JsonNode patched = patch.apply(objectMapper.convertValue(expenseGroup, JsonNode.class));
            return objectMapper.treeToValue(patched, ExpenseGroup.class);
        } catch (JsonPatchException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
