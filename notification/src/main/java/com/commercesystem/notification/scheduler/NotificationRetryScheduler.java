package com.commercesystem.notification.scheduler;

import com.commercesystem.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationRetryScheduler {

    private final NotificationService notificationService;

    @Scheduled(fixedDelay = 60000)
    public void retryFailedNotifications() {

        log.info("Retrying failed notifications");

        notificationService.retryFailedNotifications();
    }
}
