package com.pay_guard.pay_guard_bkd.controllers;

import com.pay_guard.pay_guard_bkd.dtos.requests.ReviewRequest;
import com.pay_guard.pay_guard_bkd.dtos.responses.DashboardSummaryResponse;
import com.pay_guard.pay_guard_bkd.dtos.responses.FlaggedTransactionResponse;
import com.pay_guard.pay_guard_bkd.dtos.responses.ReviewResponse;
import com.pay_guard.pay_guard_bkd.dtos.responses.TransactionResponse;
import com.pay_guard.pay_guard_bkd.services.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/dashboard")
    public ResponseEntity<DashboardSummaryResponse> dashboard() {

        return ResponseEntity.ok(
                adminService.getDashboardSummary()
        );
    }

    @GetMapping("/transactions")
    public ResponseEntity<List<TransactionResponse>> transactions() {

        return ResponseEntity.ok(
                adminService.getTransactions()
        );
    }

    @GetMapping("/transactions/{transactionId}")
    public ResponseEntity<TransactionResponse> transaction(
            @PathVariable UUID transactionId
    ) {

        return ResponseEntity.ok(
                adminService.getTransaction(transactionId)
        );
    }

    @GetMapping("/flagged")
    public ResponseEntity<List<FlaggedTransactionResponse>> flaggedTransactions() {

        return ResponseEntity.ok(
                adminService.getFlaggedTransactions()
        );
    }

    @GetMapping("/flagged/{flaggedTransactionId}")
    public ResponseEntity<FlaggedTransactionResponse> flaggedTransaction(
            @PathVariable UUID flaggedTransactionId
    ) {

        return ResponseEntity.ok(
                adminService.getFlaggedTransaction(flaggedTransactionId)
        );
    }

    @PutMapping("/flagged/{flaggedTransactionId}/review")
    public ResponseEntity<ReviewResponse> review(

            @PathVariable UUID flaggedTransactionId,

            @RequestParam UUID adminId,

            @Valid @RequestBody ReviewRequest request
    ) {

        return ResponseEntity.ok(
                adminService.reviewTransaction(
                        flaggedTransactionId,
                        adminId,
                        request

                )

        );

    }
}
