package com.commercesystem.order.dto;

public record CreateOrderResponse(

        Long orderId,

        Long paymentId,

        String orderStatus,

        String paymentStatus,

        String notificationStatus
) {
}