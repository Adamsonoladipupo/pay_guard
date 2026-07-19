package com.pay_guard.pay_guard_bkd.controllers;

import com.pay_guard.pay_guard_bkd.dtos.requests.MerchantRequest;
import com.pay_guard.pay_guard_bkd.dtos.responses.MerchantResponse;
import com.pay_guard.pay_guard_bkd.services.MerchantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/merchants")
@RequiredArgsConstructor
public class MerchantController {
    private final MerchantService merchantService;

    @PostMapping
    public ResponseEntity<MerchantResponse> createMerchant(
            @Valid @RequestBody MerchantRequest request
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        merchantService.createMerchant(request)
                );
    }

    @GetMapping
    public ResponseEntity<List<MerchantResponse>> getMerchants() {

        return ResponseEntity.ok(
                merchantService.getMerchants()
        );
    }

    @GetMapping("/{merchantId}")
    public ResponseEntity<MerchantResponse> getMerchant(
            @PathVariable UUID merchantId
    ) {

        return ResponseEntity.ok(
                merchantService.getMerchant(merchantId)
        );
    }
}
