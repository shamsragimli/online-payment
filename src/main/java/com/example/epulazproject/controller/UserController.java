package com.example.epulazproject.controller;

import com.example.epulazproject.model.request.UserPassword;
import com.example.epulazproject.model.request.UserRequestDto;
import com.example.epulazproject.model.response.*;
import com.example.epulazproject.service.serviceImpl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userService;

    @Operation(summary = "Show all users in System ", description = "Retrieve a list of all users")
    @GetMapping()
    public List<UserResponseDto> getAll() {
        return userService.getAll();
    }

    @Operation(summary = "Get user", description = "Retrieve a user by their ID")
    @GetMapping("/{id}")
    public UserResponseDto get(@PathVariable Integer id) {
        return userService.get(id);
    }

    @Operation(summary = "Get user's card", description = "Retrieve a user's card by their user ID")
    @GetMapping("/card/{id}")
    public List<CardResponseDto> getCard(@PathVariable Integer id) {
        return userService.getCards(id);
    }

    @Operation(summary = "Get user's transactions", description = "Retrieve a list of a user's transactions by their user ID")
    @GetMapping("/transaction/{id}")
    public List<TransactionResponseDto> getTransaction(@PathVariable Integer id) {
        return userService.getTransactions(id);
    }

    @Operation(summary = "Get user's balance", description = "Retrieve the balance of a user by their user ID")
    @GetMapping("/balance/{id}")
    public BalanceResponseDto getBalance(@PathVariable Integer id) {
        return userService.getBalance(id);
    }

    @Operation(summary = "Get user's favorite payment", description = "Retrieve the favorite payment of a user by their user ID")
    @GetMapping("/favoritePayment/{id}")
    public List<FavoritePaymentResponse> getFavPayment(@PathVariable Integer id){
        return userService.getFavPayment(id);
    }

    @Operation(summary = "Update user", description = "Update the information of a user by their user ID")
        @PutMapping("/{id}")
    public UserResponseDto update(@PathVariable Integer id, @RequestBody @Valid UserRequestDto userRequestDto) {
        return userService.update(id, userRequestDto);
    }

    @Operation(summary = "Change user's password", description = "Change the password of a user by their user ID")
    @PatchMapping("/password/{id}")
    public void changePassword(@PathVariable Integer id, @RequestBody @Valid UserPassword userPassword) {
        userService.changePassword(id, userPassword);
    }

    @Operation(summary = "Delete user", description = "Delete a user by their user ID")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        userService.delete(id);
    }
}
