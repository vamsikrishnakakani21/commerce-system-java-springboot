package com.commercesystem.order.client;

import java.math.BigDecimal;

public interface PaymentClient {

    Long processPayment(
            Long orderId,
            BigDecimal amount
    );
}