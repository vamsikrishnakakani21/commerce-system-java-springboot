package com.commercesystem.common.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class OrderCreatedEvent {

    private final Long orderId;
    private final String message;

}
