package com.commercesystem.notification.service;

import com.commercesystem.notification.entity.Notification;
import com.commercesystem.notification.entity.NotificationStatus;
import com.commercesystem.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class NotificationServiceImpl
        implements NotificationService {

    private final NotificationRepository notificationRepository;

    private static final int RETRY_LIMIT = 5;

    @Async
    @Override
    public void sendNotification(
            Long orderId,
            String message) {

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        log.info("Sending notification for order {}", orderId);

        boolean delivered = sendEmail(message);

        if (delivered) {
            Notification notification = Notification.builder()
                    .orderId(orderId)
                    .recipient("customer@email.com")
                    .message(message)
                    .status(NotificationStatus.SENT)
                    .retryCount(0)
                    .build();

            notificationRepository.save(notification);

            log.info("Notification sent for order {}", orderId);
        }
        else {

            Notification notification = Notification.builder()
                    .orderId(orderId)
                    .recipient("customer@email.com")
                    .message(message)
                    .status(NotificationStatus.FAILED)
                    .retryCount(0)
                    .build();

            notificationRepository.save(notification);

            // throw new RuntimeException("Notification Failed");
            log.info("Notification failed for order {}", orderId);
        }
    }

    @Override
    @Transactional
    public void retryFailedNotifications() {

        List<Notification> failedNotifications =
                notificationRepository
                        .findByStatusAndRetryCountLessThan(
                                NotificationStatus.FAILED,
                                RETRY_LIMIT);
        log.info("Found {} failed notifications",
                failedNotifications.size());

        for (Notification notification : failedNotifications) {
            processNotification(notification);
        }
    }

    private void processNotification(Notification notification) {
        log.info("Updating notification service for order {}", notification.getOrderId());

        int nextRetryCount = notification.getRetryCount() + 1;
        notification.setRetryCount(nextRetryCount);

        boolean delivered = sendEmail(notification.getMessage());
        if (delivered) {
            notification.setStatus(NotificationStatus.SENT);
            notification.setFailureReason("");
        }
        else {
            if (nextRetryCount >= RETRY_LIMIT) {
                notification.setStatus(NotificationStatus.DEAD);
                notification.setFailureReason("Max reties reached");
            } else {
                notification.setStatus(NotificationStatus.FAILED);
                notification.setFailureReason("SMTP timeout");
            }
        }
    }

    private boolean sendEmail(String message){
        return Math.random() <= 0.1;
    }
}
