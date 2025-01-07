package stu.cn.ua.production_computers.controllers;

import stu.cn.ua.production_computers.models.Orders;
import stu.cn.ua.production_computers.models.Clients;
import stu.cn.ua.production_computers.service.ClientsService;
import stu.cn.ua.production_computers.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ClientsService clientService;

    @GetMapping
    public String listOrders(Model model) {
        model.addAttribute("orders", orderService.getAllOrders());
        return "orders"; // corresponds to orders.html
    }

    @GetMapping("/add")
    public String addOrderForm(Model model) {
        model.addAttribute("order", new Orders());
        model.addAttribute("clients", clientService.getAllClients());
        return "add-order";
    }

    @PostMapping("/add")
    public String addOrder(@Valid @ModelAttribute("order") Orders order, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("clients", clientService.getAllClients());
            return "add-order";
        }
        orderService.saveOrder(order);
        return "redirect:/orders";
    }

    @GetMapping("/edit/{id}")
    public String editOrderForm(@PathVariable("id") Integer id, Model model) {
        Optional<Orders> orderOpt = orderService.findOrderById(id);
        if (orderOpt.isPresent()) {
            model.addAttribute("order", orderOpt.get());
            model.addAttribute("clients", clientService.getAllClients());
            return "edit-order";
        }
        return "redirect:/orders";
    }

    @PostMapping("/update/{id}")
    public String updateOrder(@PathVariable("id") Integer id, @Valid @ModelAttribute("order") Orders order,
                              BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("clients", clientService.getAllClients());
            return "edit-order";
        }
        orderService.updateOrder(order);
        return "redirect:/orders";
    }

    @GetMapping("/delete/{id}")
    public String deleteOrder(@PathVariable("id") Integer id) {
        orderService.deleteOrderById(id);
        return "redirect:/orders";
    }
}

