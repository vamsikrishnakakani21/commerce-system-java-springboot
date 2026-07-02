package com.commercesystem.order.service;

import com.commercesystem.common.event.OrderCreatedEvent;
import com.commercesystem.common.exception.ResourceNotFoundException;
import com.commercesystem.order.client.PaymentClient;
import com.commercesystem.order.dto.CreateOrderRequest;
import com.commercesystem.order.dto.CreateOrderResponse;
import com.commercesystem.order.entity.Order;
import com.commercesystem.order.entity.OrderStatus;
import com.commercesystem.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderServiceImpl
        implements OrderService {

    private final OrderRepository orderRepository;

    private final PaymentClient paymentClient;

    private final ApplicationEventPublisher eventPublisher;

    @Override
    public CreateOrderResponse createOrder(
            CreateOrderRequest request) {

        log.info("Creating Order...");

        Order order =
                Order.builder()
                        .productName(request.productName())
                        .quantity(request.quantity())
                        .amount(request.amount())
                        .status(OrderStatus.CREATED)
                        .build();

        orderRepository.save(order);

        log.info("Payment Processing...");

        Long paymentId =
                paymentClient.processPayment(
                        order.getId(),
                        order.getAmount());

        order.setPaymentId(paymentId);
        order.setStatus(OrderStatus.PAYMENT_COMPLETED);

        //orderRepository.save(order);

        log.info("Publishing OrderCreation event");
        eventPublisher.publishEvent(
                new OrderCreatedEvent(
                        order.getId(),
                        "Order created successfully"
                )
        );

        log.info("Order Response Returning...");

        return new CreateOrderResponse(
                order.getId(),
                paymentId,
                order.getStatus().name()
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
                order.getStatus().name()
        );
    }

}
