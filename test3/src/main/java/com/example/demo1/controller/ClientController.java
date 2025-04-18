package com.example.demo1.controller;

import com.example.demo1.model.Client;
import com.example.demo1.repository.ClientRepository;
import com.example.demo1.requests.CreateClientDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@Tag(name = "Clients", description = "Управление клиентами")
@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    @Operation(summary = "Получить всех клиентов")
    @GetMapping
    public ResponseEntity<List<Client>> getAllClients() {
        List<Client> clients = clientRepository.findAll();
        return ResponseEntity.ok(clients);
    }

    @Operation(summary = "Создать нового клиента")
    @PostMapping
    public ResponseEntity<Client> createClient(@RequestBody @Valid CreateClientDTO clientDTO) {
        Client client = new Client();
        client.setName(clientDTO.name);
        client.setAddress(clientDTO.address);
        client.setContactPerson(clientDTO.contactPerson);
        client.setPhone(clientDTO.phone);
        client.setEmail(clientDTO.email);

        Client savedClient = clientRepository.save(client);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedClient);
    }
}
