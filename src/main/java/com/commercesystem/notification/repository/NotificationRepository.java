package com.commercesystem.notification.repository;

import com.commercesystem.notification.entity.Notification;
import com.commercesystem.notification.entity.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByStatusAndRetryCountLessThan(
            NotificationStatus status,
            Integer retryCount
    );
}
