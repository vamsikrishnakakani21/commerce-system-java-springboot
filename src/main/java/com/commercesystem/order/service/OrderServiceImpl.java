package com.commercesystem.order.service;

import com.commercesystem.common.exception.ResourceNotFoundException;
import com.commercesystem.notification.entity.NotificationStatus;
import com.commercesystem.notification.service.NotificationService;
import com.commercesystem.order.dto.CreateOrderRequest;
import com.commercesystem.order.dto.CreateOrderResponse;
import com.commercesystem.order.entity.Order;
import com.commercesystem.order.entity.OrderStatus;
import com.commercesystem.order.repository.OrderRepository;
import com.commercesystem.payment.entity.PaymentStatus;
import com.commercesystem.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl
        implements OrderService {

    private final OrderRepository orderRepository;

    private final PaymentService paymentService;

    private final NotificationService notificationService;

    @Override
    public CreateOrderResponse createOrder(
            CreateOrderRequest request) {

        Order order =
                Order.builder()
                        .productName(request.productName())
                        .quantity(request.quantity())
                        .amount(request.amount())
                        .status(OrderStatus.CREATED)
                        .build();

        orderRepository.save(order);

        Long paymentId =
                paymentService.processPayment(
                        order.getId(),
                        order.getAmount());

        order.setPaymentId(paymentId);
        order.setStatus(OrderStatus.PAYMENT_COMPLETED);

        orderRepository.save(order);

        notificationService.sendNotification(
                order.getId(),
                "Order created successfully");

        return new CreateOrderResponse(
                order.getId(),
                paymentId,
                order.getStatus().name(),
                PaymentStatus.SUCCESS.name(),
                NotificationStatus.SENT.name()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public CreateOrderResponse getOrderById(Long orderId) {

        Order order =
                orderRepository.findById(orderId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Order not found with id: " + orderId));

        return new CreateOrderResponse(
                order.getId(),
                order.getPaymentId(),
                order.getStatus().name(),
                PaymentStatus.SUCCESS.name(),
                NotificationStatus.SENT.name()
        );
    }

}