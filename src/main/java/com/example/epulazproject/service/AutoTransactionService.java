package com.example.epulazproject.service;

import com.example.epulazproject.dao.AutoTransactionEntity;
import com.example.epulazproject.enums.PaymentStatus;
import com.example.epulazproject.model.request.AutoTransactionReq;
import com.example.epulazproject.model.response.AutoTransactionRes;

import java.util.List;

public interface AutoTransactionService {
    AutoTransactionRes create(AutoTransactionReq autoTransactionReq);

    void executeAutoTransaction(AutoTransactionEntity autoTransaction);

    List<AutoTransactionRes> getAll();

    List<AutoTransactionRes> getFiltered(PaymentStatus paymentStatus);
}
