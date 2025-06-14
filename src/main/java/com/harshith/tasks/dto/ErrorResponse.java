package com.harshith.tasks.dto;

public record ErrorResponse(
        int status,
        String message,
        String details
) {
}
