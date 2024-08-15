package com.example.epulazproject.controller;

import com.example.epulazproject.enums.TransactionType;
import com.example.epulazproject.model.request.*;
import com.example.epulazproject.model.response.TransactionResponseDto;
import com.example.epulazproject.service.serviceImpl.TransactionServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionServiceImpl transactionService;

    @Operation(summary = "Create Transaction with Card", description = "Initiate a transaction using a payment card")
    @PostMapping("/withCard")
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionResponseDto create(@RequestBody @Valid TransactionCardDto transactionCardDto) {
        return transactionService.createTransactionWithCard(transactionCardDto);
    }

    @Operation(summary = "Create Transaction with Balance", description = "Initiate a transaction using balance")
    @PostMapping("/withBalance")
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionResponseDto create(@RequestBody @Valid TransactionBalanceDto transactionBalanceDto) {
        return transactionService.createTransactionWithBalance(transactionBalanceDto);
    }

    @Operation(summary = "Create Transaction with Favorite Payment and Card", description = "Initiate a transaction using a favorite payment method and card")
    @PostMapping("/favPaymentWithCard")
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionResponseDto createForFavPayment(@RequestBody @Valid TransactionCardForFavPayment transactionCardForFavPayment){
        return transactionService.createTransactionWithCardForFavPayment(transactionCardForFavPayment);
    }

    @Operation(summary = "Create Transaction with Favorite Payment and Balance", description = "Initiate a transaction using a favorite payment method and balance")
    @PostMapping("/favPaymentWithBalance")
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionResponseDto createForFavPayment2(@RequestBody @Valid TransactionBalanceForFavPayment transactionBalanceForFavPayment){
        return transactionService.createTransactionWithBalanceForFavPayment(transactionBalanceForFavPayment);
    }

    @Operation(summary = "Card to Card Transfer", description = "Transfer funds from one card to another")
    @PostMapping("/cardToCard")
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionResponseDto create(@RequestBody @Valid TransactionCardToCard transactionCardToCard) {
        return transactionService.createTransactionCardToCard(transactionCardToCard);
    }

    @Operation(summary = "Get Transaction", description = "Retrieve a transaction by its ID")
    @GetMapping("/{id}")
    public TransactionResponseDto get(@PathVariable Integer id) {
        return transactionService.getById(id);
    }

    @Operation(summary = "Get All Transactions", description = "Retrieve all transactions")
    @GetMapping()
    public List<TransactionResponseDto> getAll() {
        return transactionService.getAll();
    }

    @Operation(summary = "Get Transactions by Type", description = "Retrieve transactions by their type")
    @GetMapping("/filteredByType")
    public List<TransactionResponseDto> getByType(@RequestParam TransactionType transactionType){
        return transactionService.getByType(transactionType);
    }
}
