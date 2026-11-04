package com.clients.clients.dto;

import com.clients.clients.entity.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record ClientRequestDto(
        @NotBlank(message = "Full name cannot be empty")
        @Schema(description = "ФИО")
        String fullName,
        @Size(max = 1, message = "Gender must be a single character (M/F)")
        @Schema(description = "Пол")
        String gender,
        @Schema(description = "Статус клиента (ACTIVE/INACTIVE)", example = "ACTIVE")
        Status status
) {
}
