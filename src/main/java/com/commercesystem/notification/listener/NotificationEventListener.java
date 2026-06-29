package com.commercesystem.notification.listener;

import com.commercesystem.common.event.OrderCreatedEvent;
import com.commercesystem.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationEventListener {

    private final NotificationService notificationService;

    @EventListener
    public void handleOrderCreated(
            OrderCreatedEvent event) {

        log.info("Received OrderCreatedEvent : {}", event.toString());
        notificationService.sendNotification(
                event.getOrderId(),
                event.getMessage()
        );
    }
}
