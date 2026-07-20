package com.pay_guard.pay_guard_bkd.controllers;

import com.pay_guard.pay_guard_bkd.dtos.requests.TransactionRequest;
import com.pay_guard.pay_guard_bkd.dtos.responses.TransactionResponse;
import com.pay_guard.pay_guard_bkd.services.TransactionServiceImp;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionServiceImp transactionServiceImp;

    @PostMapping
    public ResponseEntity<TransactionResponse> processTransaction(
            @Valid @RequestBody TransactionRequest request
    ) {

        TransactionResponse response =
                transactionServiceImp.processTransaction(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<TransactionResponse> getTransaction(
            @PathVariable UUID transactionId
    ) {

        return ResponseEntity.ok(
                transactionServiceImp.getTransaction(transactionId)
        );
    }
}
