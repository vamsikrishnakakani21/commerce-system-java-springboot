package com.commercesystem.payment.service;

import com.commercesystem.payment.entity.Payment;
import com.commercesystem.payment.entity.PaymentStatus;
import com.commercesystem.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl
        implements PaymentService {

    private final PaymentRepository paymentRepository;

    @Override
    public Long processPayment(
            Long orderId,
            BigDecimal amount) {

        Payment payment =
                Payment.builder()
                        .orderId(orderId)
                        .amount(amount)
                        .status(PaymentStatus.SUCCESS)
                        .build();

        paymentRepository.save(payment);

        return payment.getId();
    }
}
