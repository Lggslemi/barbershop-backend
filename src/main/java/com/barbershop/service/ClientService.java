package com.barbershop.service;

import com.barbershop.entity.Client;
import com.barbershop.repository.ClientRepository;
import com.barbershop.repository.ClientRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    public Client findById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado"));
    }

    public Client save(Client client) {
        return clientRepository.save(client);
    }

    public Client update(Long id, Client client) {
        Client existingClient = findById(id);
        existingClient.setName(client.getName());
        existingClient.setPhone(client.getPhone());
        return clientRepository.save(existingClient);
    }

    public void delete(Long id) {
        Client existingClient = findById(id);
        clientRepository.delete(existingClient);
    }
}