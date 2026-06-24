package com.commercesystem.order.service;

import com.commercesystem.order.dto.CreateOrderRequest;
import com.commercesystem.order.dto.CreateOrderResponse;

public interface OrderService {

    CreateOrderResponse createOrder(
            CreateOrderRequest request);

    CreateOrderResponse getOrderById(Long orderId);
}
