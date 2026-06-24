package com.commercesystem.notification.client;

import com.commercesystem.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationClientImpl implements NotificationClient {

    private final NotificationService notificationService;

    @Override
    public void sendNotification(
            Long orderId,
            String message) {

        notificationService
                .sendNotification(
                        orderId, message
                );
    }
}
