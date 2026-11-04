package com.clients.clients.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record ClientIdResponseDto(
        @Schema(description = "ID")
        Long id
) {
}
