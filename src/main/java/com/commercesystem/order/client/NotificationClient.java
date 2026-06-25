package com.commercesystem.order.client;

public interface NotificationClient {

    void sendNotification(
            Long orderId,
            String message
    );
}
