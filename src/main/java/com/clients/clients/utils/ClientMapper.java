package com.clients.clients.utils;

import com.clients.clients.dto.ClientIdResponseDto;
import com.clients.clients.dto.ClientRequestDto;
import com.clients.clients.dto.ClientResponseDto;
import com.clients.clients.entity.Client;
import com.clients.clients.entity.enums.Status;

import java.time.OffsetDateTime;

public class ClientMapper {

    public static ClientResponseDto toResponse(Client client) {
        return new ClientResponseDto(
                client.getId(),
                client.getFullName(),
                client.getGender(),
                client.getStatus(),
                client.getCreateDttm(),
                client.getModifyDttm()
        );
    }

    public static ClientIdResponseDto toId(Client client) {
        return new ClientIdResponseDto(client.getId());
    }

    public static Client createEntity(ClientRequestDto request) {
        var client = new Client();
        updateMainFields(client, request);
        var now =  OffsetDateTime.now();
        client.setCreateDttm(now);
        client.setModifyDttm(now);
        return client;
    }

    public static void updateEntity(Client client, ClientRequestDto request) {
        updateMainFields(client, request);
        client.setModifyDttm(OffsetDateTime.now());
    }

    public static void updateMainFields(Client client,  ClientRequestDto request) {
        client.setFullName(request.fullName());
        client.setGender(request.gender());

        if (request.status() != null) {
            client.setStatus(request.status());
        }

        else if (client.getId() == null){
            client.setStatus(Status.ACTIVE);
        }
    }
}
