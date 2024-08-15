package com.example.epulazproject.controller;

import com.example.epulazproject.enums.SupportStatus;
import com.example.epulazproject.model.response.ContactProperties;
import com.example.epulazproject.model.request.SupportRequestDto;
import com.example.epulazproject.model.response.SupportResponseDto;
import com.example.epulazproject.model.response.SupportUpdateDto;
import com.example.epulazproject.service.serviceImpl.SupportServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/support")
@RequiredArgsConstructor
public class SupportController {
    private final SupportServiceImpl supportService;

    @Autowired
    private ContactProperties contactProperties;

    @Operation(summary = "Get support", description = "Retrieve support information by its ID")
    @GetMapping("/{id}")
    public SupportResponseDto getById(@PathVariable Integer id) {
        return supportService.getById(id);
    }

    @Operation(summary = "Get all support information", description = "Retrieve a list of all support information entries")
    @GetMapping
    public List<SupportResponseDto> getAll(){
        return supportService.getAll();
    }

    @Operation(summary = "Get support by status", description = "Retrieve support information by status")
    @GetMapping("/filtered")
    public List<SupportResponseDto> getByStatus(@RequestParam SupportStatus supportStatus){
        return supportService.getFiltered(supportStatus);
    }

    @Operation(summary = "Create support", description = "Create a new support entry")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SupportResponseDto create(@RequestBody @Valid SupportRequestDto supportRequestDto){
        return supportService.create(supportRequestDto);
    }

    @Operation(summary = "Update support", description = "Update support information by its ID")
    @PutMapping("/{id}")
    public SupportResponseDto update(@PathVariable Integer id, @RequestBody @Valid SupportUpdateDto supportUpdateDto){
        return supportService.update(id,supportUpdateDto);
    }

    @Operation(summary = "Delete support", description = "Delete support information by its ID")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id){
        supportService.delete(id);
    }

    @Operation(summary = "Get contact information", description = "Retrieve contact information")
    @GetMapping("/contactInfo")
    public ContactProperties getContactInfo() {
        return contactProperties;
    }








    }
