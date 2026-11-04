package com.clients.clients.dto;

import com.clients.clients.entity.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.OffsetDateTime;

public record ClientResponseDto(
        @Schema(description = "ID")
        Long id,
        @Schema(description = "ФИО")
        String fullName,
        @Schema(description = "Пол")
        String gender,
        @Schema(description = "Статус")
        Status status,
        @Schema(description = "Дата создания")
        OffsetDateTime createDttm,
        @Schema(description = "Дата обновления")
        OffsetDateTime modifyDttm
) {
}
