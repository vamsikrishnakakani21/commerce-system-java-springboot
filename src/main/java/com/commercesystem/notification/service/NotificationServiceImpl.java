package com.commercesystem.notification.service;

import com.commercesystem.notification.entity.Notification;
import com.commercesystem.notification.entity.NotificationStatus;
import com.commercesystem.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class NotificationServiceImpl
        implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Override
    public void sendNotification(
            Long orderId,
            String message) {

        Notification notification =
                Notification.builder()
                        .orderId(orderId)
                        .recipient("customer@email.com")
                        .message(message)
                        .status(NotificationStatus.SENT)
                        .build();

        notificationRepository.save(notification);

        log.info("Notification sent for order {}", orderId);
    }
}