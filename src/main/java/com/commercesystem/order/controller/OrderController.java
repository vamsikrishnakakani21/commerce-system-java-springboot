package com.commercesystem.order.controller;

import com.commercesystem.common.dto.ApiResponse;
import com.commercesystem.order.dto.CreateOrderRequest;
import com.commercesystem.order.dto.CreateOrderResponse;
import com.commercesystem.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<ApiResponse<CreateOrderResponse>> createOrder(
            @Valid @RequestBody CreateOrderRequest request) {

        CreateOrderResponse response =
                orderService.createOrder(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        new ApiResponse<>(
                                true,
                                "Order created successfully",
                                response
                        )
                );
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse<CreateOrderResponse>>
    getOrderById(
            @PathVariable Long orderId) {

        CreateOrderResponse response =
                orderService.getOrderById(orderId);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Order fetched successfully",
                        response
                )
        );
    }
}