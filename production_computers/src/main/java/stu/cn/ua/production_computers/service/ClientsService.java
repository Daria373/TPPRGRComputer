package stu.cn.ua.production_computers.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stu.cn.ua.production_computers.models.Clients;
import stu.cn.ua.production_computers.repository.ClientsRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ClientsService {

    @Autowired
    private ClientsRepository clientsRepository;

    public List<Clients> getAllClients() {
        return clientsRepository.findAll();
    }

    public Optional<Clients> findClientById(Integer id) {
        return clientsRepository.findById(id);
    }

    public void saveClient(Clients client) {
        clientsRepository.save(client);
    }

    public boolean updateClient(Integer id, Clients updatedClient) {
        Optional<Clients> existingClientOpt = clientsRepository.findById(id);

        if (existingClientOpt.isPresent()) {
            Clients existingClient = existingClientOpt.get();
            existingClient.setName(updatedClient.getName());
            existingClient.setSurname(updatedClient.getSurname());
            existingClient.setPhone(updatedClient.getPhone());
            existingClient.setEmail(updatedClient.getEmail());
            clientsRepository.save(existingClient);
            return true;
        }
        return false;
    }

    public void deleteClientById(Integer id) {
        clientsRepository.deleteById(id);
    }
}