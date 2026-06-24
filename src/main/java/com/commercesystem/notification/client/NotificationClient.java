package com.commercesystem.notification.client;

public interface NotificationClient {

    void sendNotification(
            Long orderId,
            String message
    );
}
