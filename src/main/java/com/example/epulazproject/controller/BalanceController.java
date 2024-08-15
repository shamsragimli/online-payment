package com.example.epulazproject.controller;

import com.example.epulazproject.model.request.BalanceRequestDto;
import com.example.epulazproject.model.request.FromCashbackToBalanceDto;
import com.example.epulazproject.model.response.BalanceResponseDto;
import com.example.epulazproject.service.serviceImpl.BalanceServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/balance")
@RequiredArgsConstructor
public class BalanceController {
    private final BalanceServiceImpl balanceService;

    @Operation(summary = "Transfer from card to balance", description = "Transfer an amount from a card to a balance")
    @PutMapping("/transferFromCard")
    public BalanceResponseDto transferFromCard(@RequestBody @Valid BalanceRequestDto balanceRequestDto) {
        return balanceService.transferFromCard(balanceRequestDto);
    }

    @Operation(summary = "Transfer from balance to card", description = "Transfer an amount from a balance to a card")
    @PutMapping("/transferToCard")
    public BalanceResponseDto transferToCard(@RequestBody @Valid BalanceRequestDto balanceRequestDto) {
        return balanceService.transferToCard(balanceRequestDto);
    }

    @Operation(summary = "Transfer from cashback balance to primary balance", description = "Transfer an amount from a cashback balance to a primary balance")
    @PutMapping("/fromCashbackToBalance")
    public BalanceResponseDto fromCashbackToCard(FromCashbackToBalanceDto fromCashbackToBalanceDto) {
        return balanceService.fromCashbackToCard(fromCashbackToBalanceDto);
    }

    @Operation(summary = "Get all balances", description = "Retrieve a list of all balances")
    @GetMapping()
    public List<BalanceResponseDto> getAll() {
        return balanceService.getAll();
    }

    @Operation(summary = "Get balance", description = "Retrieve a balance by its ID")
    @GetMapping("/{id}")
    public BalanceResponseDto get(@PathVariable Integer id) {
        return balanceService.get(id);
    }

}

