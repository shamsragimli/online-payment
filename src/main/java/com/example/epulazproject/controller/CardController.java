package com.example.epulazproject.controller;

import com.example.epulazproject.model.request.CardRequestDto;
import com.example.epulazproject.model.response.CardResponseDto;
import com.example.epulazproject.service.serviceImpl.CardServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/card")
@RequiredArgsConstructor
public class CardController {
    private final CardServiceImpl cardService;

    @Operation(summary = "Get all card list", description = "Retrieve a list of all cards")
    @GetMapping
    public List<CardResponseDto> getAll() {
        return cardService.getAll();
    }

    @Operation(summary = "Get card", description = "Retrieve a card by its ID")
    @GetMapping("/{id}")
    public CardResponseDto get(@PathVariable Integer id) {
        return cardService.get(id);
    }

    @Operation(summary = "Add a new card")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @Valid CardRequestDto cardRequestDto) {
        cardService.create(cardRequestDto);
    }

    @Operation(summary = "Update card", description = "Update an existing card by its ID")
    @PutMapping("/{id}")
    public void update(@PathVariable Integer id, @RequestBody CardRequestDto cardRequestDto) {
        cardService.update(id, cardRequestDto);
    }

    @Operation(summary = "Delete card", description = "Delete an existing card by its ID")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        cardService.delete(id);
    }
}

