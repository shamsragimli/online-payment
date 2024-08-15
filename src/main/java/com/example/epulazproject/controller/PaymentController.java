package com.example.epulazproject.controller;

import com.example.epulazproject.model.request.FavoritePaymentDto;
import com.example.epulazproject.model.request.FieldRequestDto;
import com.example.epulazproject.model.response.FavoritePaymentResponse;
import com.example.epulazproject.model.response.FieldResponseDto;
import com.example.epulazproject.model.request.PaymentRequestDto;
import com.example.epulazproject.model.response.PaymentResponseDto;
import com.example.epulazproject.service.serviceImpl.PaymentServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentServiceImpl paymentService;

    @Operation(summary = "Get all payment categories", description = "Retrieve a list of all payment categories")
    @GetMapping
    public List<PaymentResponseDto> getAll() {
        return paymentService.getAll();
    }

    @Operation(summary = "Get payment", description = "Retrieve payment details by its ID")
    @GetMapping("/{id}")
    public PaymentResponseDto get(@PathVariable Integer id) {
        return paymentService.getById(id);
    }

    @Operation(summary = "Create a new payment", description = "Create a new payment entry")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentResponseDto add(@RequestBody @Valid PaymentRequestDto paymentRequestDto) {
        return paymentService.add(paymentRequestDto);
    }

    @Operation(summary = "Create favorite payment", description = "Create a new favorite payment entry")
    @PostMapping("/favoritePayment")
    @ResponseStatus(HttpStatus.CREATED)
    public FavoritePaymentResponse createFav(@RequestBody FavoritePaymentDto favoritePaymentDto) {
        return paymentService.createFav(favoritePaymentDto);
    }

    @Operation(summary = "Get favorite payment", description = "Retrieve a favorite payment entry by its ID")
    @GetMapping("/favoritePayment/{id}")
    public FavoritePaymentResponse getFav(@PathVariable Integer id) {
        return paymentService.getFav(id);
    }


    @Operation(summary = "Get all favorite payments", description = "Retrieve a list of all favorite payment entries")
    @GetMapping("/favoritePayments")
    public List<FavoritePaymentResponse> getAllFav(){
        return paymentService.getAllFav();
    }

    @Operation(summary = "Delete favorite payment", description = "Delete a favorite payment entry by its ID")
    @DeleteMapping("/favoritePayment/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFav(@PathVariable Integer id) {
        paymentService.deleteFav(id);
    }

    @Operation(summary = "Update payment", description = "Update an existing payment entry by its ID")
    @PutMapping("/{id}")
    public PaymentResponseDto update(@PathVariable Integer id, @RequestBody @Valid PaymentRequestDto paymentRequestDto) {
        return paymentService.update(id, paymentRequestDto);
    }

    @Operation(summary = "Delete payment", description = "Delete a payment entry by its ID")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        paymentService.delete(id);
    }

    @Operation(summary = "Add field to payment", description = "Add a new field to an existing payment")
    @PostMapping("/field")
    @ResponseStatus(HttpStatus.CREATED)
    public FieldResponseDto addFieldForPayment(@RequestBody @Valid FieldRequestDto fieldRequestDto) {
        return paymentService.addFieldForPayment(fieldRequestDto);
    }

    @Operation(summary = "Get fields for payment", description = "Retrieve fields of a payment by its ID")
    @GetMapping("/fields/{paymentId}")
    public List<FieldResponseDto> getFieldsForPayment(@PathVariable Integer paymentId) {
        return paymentService.getFieldsForPayment(paymentId);
    }
}


