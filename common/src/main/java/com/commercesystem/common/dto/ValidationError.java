package com.commercesystem.common.dto;

public record ValidationError(
        String field,
        String error
) {
}
