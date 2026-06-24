package com.commercesystem.notification.entity;

import com.commercesystem.common.audit.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;

    private String recipient;

    private String message;

    @Enumerated(EnumType.STRING)
    private NotificationStatus status;
}