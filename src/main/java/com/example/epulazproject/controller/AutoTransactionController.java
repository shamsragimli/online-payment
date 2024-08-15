package com.example.epulazproject.controller;

import com.example.epulazproject.enums.PaymentStatus;
import com.example.epulazproject.model.request.AutoTransactionReq;
import com.example.epulazproject.model.response.AutoTransactionRes;
import com.example.epulazproject.service.AutoTransactionService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/autoTransaction")
@RequiredArgsConstructor
public class AutoTransactionController {
    private final AutoTransactionService autoTransactionService;

    @Operation(summary = "Create auto-transaction", description = "Creates and schedules automatic transactions")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AutoTransactionRes create(@RequestBody @Valid AutoTransactionReq autoTransactionReq) {
        return autoTransactionService.create(autoTransactionReq);
    }

    @Operation(summary = "Get all auto-transactions", description = "Retrieve a list of all scheduled automatic transactions")
    @GetMapping
    public List<AutoTransactionRes> getAll() {
        return autoTransactionService.getAll();
    }

    @Operation(summary = "Get auto-transactions by payment status", description = "Retrieve auto-transactions based on their payment status")
    @GetMapping("/getFiltered")
    public List<AutoTransactionRes> getFiltered(@RequestParam PaymentStatus paymentStatus) {
        return autoTransactionService.getFiltered(paymentStatus);
    }
}
