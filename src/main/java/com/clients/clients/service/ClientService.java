package com.clients.clients.service;

import com.clients.clients.dto.ClientAllResponseDto;
import com.clients.clients.dto.ClientIdResponseDto;
import com.clients.clients.dto.ClientRequestDto;
import com.clients.clients.dto.ClientResponseDto;
import com.clients.clients.entity.Client;
import com.clients.clients.exception.ClientNotFoundException;
import com.clients.clients.repository.ClientRepository;
import com.clients.clients.utils.ClientMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ClientService {

    private final ClientRepository repository;

    public ClientResponseDto getById(Long id) {
        var client = findByIdOrThrow(id);
        return ClientMapper.toResponse(client);
    }

    public ClientAllResponseDto getAll() {
        var clients = repository.findAll()
                .stream()
                .map(ClientMapper::toResponse)
                .toList();
        return new ClientAllResponseDto(clients);
    }

    @Transactional
    public ClientIdResponseDto create(ClientRequestDto request) {
        log.info("Creating new client");
        var client = ClientMapper.createEntity(request);
        repository.save(client);
        log.info("Client successfully created with id {}", client.getId());
        return ClientMapper.toId(client);
    }

    @Transactional
    public ClientIdResponseDto update(Long id, ClientRequestDto request) {
        log.info("Updating new client");
        var client = findByIdOrThrow(id);
        ClientMapper.updateEntity(client, request);
        log.info("Client successfully updated with id {}", client.getId());
        return ClientMapper.toId(client);
    }

    @Transactional
    public ClientIdResponseDto delete(Long id) {
        log.info("Deleting client");
        var client = findByIdOrThrow(id);
        repository.delete(client);
        log.info("Client successfully deleted with id {}", client.getId());
        return ClientMapper.toId(client);
    }

    private Client findByIdOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Client not found with id={}", id);
                    return new ClientNotFoundException("Client not found with id: " + id);
                });
    }
}
