package com.commercesystem.notification.service;

public interface NotificationService {

    void sendNotification(
            Long orderId,
            String message);
}