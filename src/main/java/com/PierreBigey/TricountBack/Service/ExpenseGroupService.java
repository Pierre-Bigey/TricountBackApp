package com.PierreBigey.TricountBack.Service;

import com.PierreBigey.TricountBack.Entity.Expense;
import com.PierreBigey.TricountBack.Entity.ExpenseGroup;
import com.PierreBigey.TricountBack.Entity.ExpenseParticipation;
import com.PierreBigey.TricountBack.Entity.UserAccount;
import com.PierreBigey.TricountBack.Exception.ResourceNotFoundException;
import com.PierreBigey.TricountBack.Exception.UserNotInGroupException;
import com.PierreBigey.TricountBack.Payload.Request.ExpenseGroupModel;
import com.PierreBigey.TricountBack.Payload.Response.UserBalance;
import com.PierreBigey.TricountBack.Repository.ExpenseGroupRepository;
import com.PierreBigey.TricountBack.Repository.UserAccountRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ExpenseGroupService {

    @Autowired
    private ExpenseGroupRepository expenseGroupRepository;

    @Autowired
    private UserAccountRepository userAccountRepository;

    //Create a new expense groups
    public ExpenseGroup createExpenseGroup(ExpenseGroupModel expenseGroupModel) {
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
        return expenseGroupRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Expense group with ID " + id + " not found"));
    }

    //Update an expense groups
    public void updateOne(long id, ExpenseGroupModel expenseGroupModel) {
        if (expenseGroupRepository.findById(id).isEmpty()) throw new ResourceNotFoundException("Expense group with ID " + id + " not found");
        expenseGroupRepository.updateById(expenseGroupModel.getGroupname(), expenseGroupModel.getDescription(), id);
    }

    public ExpenseGroup patchOne(long id, JsonPatch patch) {
        var expenseGroup = expenseGroupRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Expense group with ID " + id + " not found"));
        var expenseGroupPatched = applyPatchToExpenseGroup(patch, expenseGroup);
        return expenseGroupRepository.save(expenseGroupPatched);
    }

    public ExpenseGroup addUsersToGroup(long groupId, List<Long> userIds) {
        ExpenseGroup expenseGroup = expenseGroupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Group with ID %d not found", groupId)));

        for(long user_id : userIds) {
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


    /**
     * Compute and return the balance of each user in the group
     *
     * @param groupId the id of the group
     * @return the list of UserBalance
     */
    public List<UserBalance> getBalance(long groupId) {
        ExpenseGroup group = expenseGroupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Group with ID %d not found", groupId)));

        // List of expenses relative to the group
        List<Expense> expenses_of_group = group.getExpenses();

        // Create a map to store user balances with userId as key
        Map<Long, UserBalance> userBalanceMap = new HashMap<>();

        // Initialize user balances
        for (Long userId : group.getMembers_ids()) {
            userBalanceMap.put(userId, new UserBalance(userId));
        }

        // Iterate over expenses and update user balances
        for (Expense expense : group.getExpenses()) {
            double positive = expense.getAmount(); // Initialize positive balance with author's expense
            int expenseTotalWeight = expense.getSumOfWeight();

            // Update positive balance for author
            Long authorId = expense.getAuthor().getId();
            if (!userBalanceMap.containsKey(authorId)) {
                throw new UserNotInGroupException(String.format("Author's ID (%d) not found in group ID (%d)", authorId, groupId));
            }
            UserBalance authorBalance = userBalanceMap.get(authorId);
            authorBalance.setBalance(authorBalance.getBalance() + positive);

            // Update negative balance for participants
            for (ExpenseParticipation participation : expense.getParticipations()) {
                double negative = expense.getAmount() * participation.getWeight() / expenseTotalWeight;
                Long userId = participation.getUser_id();
                if (!userBalanceMap.containsKey(userId)) {
                    throw new UserNotInGroupException(String.format("User's ID (%d) not found in group ID (%d)", userId, groupId));
                }
                UserBalance participantBalance = userBalanceMap.get(userId);
                participantBalance.setBalance(participantBalance.getBalance() - negative);
                participantBalance.setTotalExpense(participantBalance.getTotalExpense() + negative);
            }
        }

        // Convert map values to list and return
        return new ArrayList<>(userBalanceMap.values());
    }
}
