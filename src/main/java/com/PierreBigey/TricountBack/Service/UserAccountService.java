package com.PierreBigey.TricountBack.Service;

import com.PierreBigey.TricountBack.Entity.ExpenseGroup;
import com.PierreBigey.TricountBack.Entity.UserAccount;
import com.PierreBigey.TricountBack.Exception.ResourceNotFoundException;
import com.PierreBigey.TricountBack.Payload.Request.UserAccountModel;
import com.PierreBigey.TricountBack.Repository.ExpenseGroupRepository;
import com.PierreBigey.TricountBack.Repository.UserAccountRepository;
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
public class UserAccountService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private ExpenseGroupRepository expenseGroupRepository;

    //Create a new user account
    public UserAccount createUserAccount(UserAccountModel userAccountModel) {
        UserAccount userAccountToSave = UserAccount.builder()
                .username(userAccountModel.getUsername())
                .firstname(userAccountModel.getFirstname())
                .lastname(userAccountModel.getLastname())
                .password(userAccountModel.getPassword())
                .build();

        if (userAccountModel.getGroups_ids() != null && !userAccountModel.getGroups_ids().isEmpty()) {
            List<ExpenseGroup> groups = expenseGroupRepository.findAllById(userAccountModel.getGroups_ids());
            userAccountToSave.setExpenseGroups(groups);
        }
        return userAccountRepository.save(userAccountToSave);
    }

    //Get all user accounts
    public List<UserAccount> getAllUserAccounts() {
        return userAccountRepository.findAll().stream().sorted(Comparator.comparing(UserAccount::getId)).toList();
    }

    //Get a user account by id
    public UserAccount getUserAccountById(Long id) {
        return userAccountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User with ID " + id + " not found"));
    }

    //Update a user account
    public void updateOne(long id, UserAccountModel userAccountModel) {
        if (userAccountRepository.findById(id).isEmpty()) throw new ResourceNotFoundException("User with ID " + id + " not found");
        userAccountRepository.updateById(userAccountModel.getUsername(), userAccountModel.getFirstname(), userAccountModel.getLastname(), userAccountModel.getPassword(), id);
    }

    public UserAccount patchOne(long id, JsonPatch patch) {
        var userAccount = userAccountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User with ID " + id + " not found"));
        var userAccountPatched = applyPatchToUserAccount(patch, userAccount);
        return userAccountRepository.save(userAccountPatched);
    }

    public UserAccount addGroupsToUser(long userId, List<Long> groupIds) {
        UserAccount userAccount = userAccountRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("User with ID %d not found", userId)));

        for(long group_id : groupIds) {
            ExpenseGroup expenseGroup = expenseGroupRepository.findById(group_id)
                    .orElseThrow(() -> new ResourceNotFoundException(String.format("Group with ID %d not found", group_id)));
            userAccountRepository.AddGroupToUser(userId, group_id);
        }

        UserAccount userAccountUpdated = userAccountRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("User with ID %d not found", userId)));


        return userAccountUpdated;
    }

    public void deleteById(long id) {
        userAccountRepository.deleteById(id);
    }

    public void deleteAll() {
        userAccountRepository.deleteAll();
    }

    private UserAccount applyPatchToUserAccount(JsonPatch patch, UserAccount userAccount) {
        try {
            var objectMapper = new ObjectMapper();
            JsonNode patched = patch.apply(objectMapper.convertValue(userAccount, JsonNode.class));
            return objectMapper.treeToValue(patched, UserAccount.class);
        } catch (JsonPatchException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


}
