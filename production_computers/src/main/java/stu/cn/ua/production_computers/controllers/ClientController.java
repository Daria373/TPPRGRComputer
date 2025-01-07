package stu.cn.ua.production_computers.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import stu.cn.ua.production_computers.models.Clients;
import stu.cn.ua.production_computers.service.ClientsService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientsService clientsService;

    @GetMapping
    public String getAllClients(Model model) {
        List<Clients> clients = clientsService.getAllClients();
        model.addAttribute("clients", clients);
        return "clients";
    }

    @GetMapping("/add")
    public String showAddClientForm(Model model) {
        model.addAttribute("clients", new Clients());
        return "add-client";
    }

    @PostMapping
    public String createClient(@ModelAttribute("clients") Clients client) {
        clientsService.saveClient(client);
        return "redirect:/clients";
    }

    @GetMapping("/edit/{id}")
    public String showEditClientForm(@PathVariable Integer id, Model model) {
        Optional<Clients> client = clientsService.findClientById(id);
        if (client.isPresent()) {
            model.addAttribute("client", client.get());
            return "edit-clients";
        }
        return "redirect:/clients";
    }

    @PostMapping("/edit/{id}")
    public String updateClient(@PathVariable Integer id, @ModelAttribute("client") Clients updatedClient) {
        clientsService.updateClient(id, updatedClient);
        return "redirect:/clients";
    }

    @GetMapping("/delete/{id}")
    public String deleteClient(@PathVariable Integer id) {
        clientsService.deleteClientById(id);
        return "redirect:/clients";
    }
}
