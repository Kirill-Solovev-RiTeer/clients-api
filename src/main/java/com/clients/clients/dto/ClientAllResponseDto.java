package com.clients.clients.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record ClientAllResponseDto(
        @Schema(description = "Список всех клиентов")
        List<ClientResponseDto> clients
) {
}
