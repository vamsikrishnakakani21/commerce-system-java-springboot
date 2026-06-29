package com.commercesystem.common.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderCreatedEvent {

    private final Long orderId;
    private final String message;

}
