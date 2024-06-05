package com.PierreBigey.TricountBack.Service;

import com.PierreBigey.TricountBack.Entity.ExpenseGroup;
import com.PierreBigey.TricountBack.Entity.UserAccount;
import com.PierreBigey.TricountBack.Exception.ResourceNotFoundException;
import com.PierreBigey.TricountBack.Payload.ExpenseGroupModel;
import com.PierreBigey.TricountBack.Repository.ExpenseGroupRepository;
import com.PierreBigey.TricountBack.Repository.UserAccountRepository;
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

    @Autowired
    private UserAccountRepository userAccountRepository;

    //Create a new expense groups
    public ExpenseGroup createExpenseGroup(ExpenseGroupModel expenseGroupModel){
        ExpenseGroup expenseGroupToSave = ExpenseGroup.builder()
                .groupname(expenseGroupModel.getGroupname())
                .description(expenseGroupModel.getDescription())
                .build();
        if (expenseGroupModel.getMembers_id() != null && !expenseGroupModel.getMembers_id().isEmpty()) {
            List<UserAccount> users = userAccountRepository.findAllById(expenseGroupModel.getMembers_id());
            expenseGroupToSave.setMembers(users);
        }
        return expenseGroupRepository.save(expenseGroupToSave);
    }

    //Get all expense groups
    public List<ExpenseGroup> getAllExpenseGroups() {
        return expenseGroupRepository.findAll().stream().sorted(Comparator.comparing(ExpenseGroup::getId)).toList();
    }

    //Get an expense groups by id
    public ExpenseGroup getExpenseGroupById(Long id) {
        return expenseGroupRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    //Update an expense groups
    public void updateOne(long id, ExpenseGroupModel expenseGroupModel) {
        if (expenseGroupRepository.findById(id).isEmpty()) throw new EntityNotFoundException();
        expenseGroupRepository.updateById(expenseGroupModel.getGroupname(), expenseGroupModel.getDescription(), id);
    }

    public ExpenseGroup patchOne(long id, JsonPatch patch) {
        var expenseGroup = expenseGroupRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        var expenseGroupPatched = applyPatchToExpenseGroup(patch, expenseGroup);
        return expenseGroupRepository.save(expenseGroupPatched);
    }

    public ExpenseGroup addUsersToGroup(long groupId, List<Long> userIds) {
        ExpenseGroup expenseGroup = expenseGroupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Group with ID %d not found", groupId)));

        for(long user_id : userIds){
            UserAccount userAccount = userAccountRepository.findById(user_id)
                    .orElseThrow(() -> new ResourceNotFoundException(String.format("User with ID %d not found", user_id)));
            expenseGroupRepository.AddUserToGroup(groupId, user_id);
        }

        ExpenseGroup expenseGroupUpdated = expenseGroupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Group with ID %d not found", groupId)));

        return expenseGroupUpdated;
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
