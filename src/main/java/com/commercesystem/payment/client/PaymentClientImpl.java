package com.commercesystem.payment.client;

import com.commercesystem.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class PaymentClientImpl
        implements PaymentClient {

    private final PaymentService paymentService;

    @Override
    public Long processPayment(
            Long orderId,
            BigDecimal amount) {

        return paymentService.processPayment(
                orderId,
                amount
        );
    }
}