package com.clients.clients.controller;

import com.clients.clients.dto.ClientAllResponseDto;
import com.clients.clients.dto.ClientIdResponseDto;
import com.clients.clients.dto.ClientRequestDto;
import com.clients.clients.dto.ClientResponseDto;
import com.clients.clients.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/clients")
@Tag(name = "Клиенты", description = "Позволяет взаимодействовать с клиентами")
public class ClientController {

    private final ClientService service;

    @GetMapping
    @Operation(summary = "Получить всех клиентов")
    public ClientAllResponseDto getAll() {return service.getAll();}

    @GetMapping("/{id}")
    @Operation(summary = "Получить клиента по ID")
    public ClientResponseDto getById(@PathVariable Long id) {return service.getById(id);}

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Создать нового клиента")
    public ClientIdResponseDto create(@Valid @RequestBody ClientRequestDto request) {return service.create(request);}

    @PutMapping("/{id}")
    @Operation(summary = "Обновить данные клиента по ID")
    public ClientIdResponseDto update(@PathVariable Long id, @Valid @RequestBody ClientRequestDto request) {return service.update(id, request);}

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить данные клиента по ID")
    public ClientIdResponseDto delete(@PathVariable Long id) {return service.delete(id);}
}
