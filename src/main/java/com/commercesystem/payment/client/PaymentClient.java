package com.commercesystem.payment.client;

import java.math.BigDecimal;

public interface PaymentClient {

    Long processPayment(
            Long orderId,
            BigDecimal amount
    );
}