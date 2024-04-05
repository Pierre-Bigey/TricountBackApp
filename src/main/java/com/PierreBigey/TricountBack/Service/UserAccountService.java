package com.PierreBigey.TricountBack.Service;

import com.PierreBigey.TricountBack.Entity.UserAccount;
import com.PierreBigey.TricountBack.Payload.UserAccountModel;
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
public class UserAccountService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    //Create a new user account
    public UserAccount createUserAccount(UserAccountModel userAccountModel) {
        var userAccountToSave = UserAccount.builder()
                .firstname(userAccountModel.getFirstname())
                .lastname(userAccountModel.getLastname())
                .build();
        return userAccountRepository.save(userAccountToSave);
    }

    //Get all user accounts
    public List<UserAccount> getAllUserAccounts() {
        return userAccountRepository.findAll().stream().sorted(Comparator.comparing(UserAccount::getId)).toList();
    }

    //Get a user account by id
    public UserAccount getUserAccountById(Long id) {
        return userAccountRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    //Update a user account
    public void updateOne(long id, UserAccountModel userAccountModel) {
        if (userAccountRepository.findById(id).isEmpty()) throw new EntityNotFoundException();
        userAccountRepository.updateById(userAccountModel.getFirstname(), userAccountModel.getLastname(), id);
    }

    public UserAccount patchOne(long id, JsonPatch patch) {
        var userAccount = userAccountRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        var userAccountPatched = applyPatchToUserAccount(patch, userAccount);
        return userAccountRepository.save(userAccountPatched);
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
