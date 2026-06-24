package com.commercesystem.payment.service;

import java.math.BigDecimal;

public interface PaymentService {

    Long processPayment(
            Long orderId,
            BigDecimal amount);
}