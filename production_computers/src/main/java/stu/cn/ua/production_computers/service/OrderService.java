package stu.cn.ua.production_computers.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stu.cn.ua.production_computers.models.Orders;
import stu.cn.ua.production_computers.repository.OrdersRepository;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrdersRepository orderRepository;

    public List<Orders> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Orders> findOrderById(Integer id) {
        return orderRepository.findById(id);
    }

    public void saveOrder(Orders order) {
        orderRepository.save(order);
    }

    public void updateOrder(Orders updatedOrder) {
        Optional<Orders> existingOrderOpt = orderRepository.findById(updatedOrder.getOrderId());

        if (existingOrderOpt.isPresent()) {
            Orders existingOrder = existingOrderOpt.get();
            existingOrder.setClient(updatedOrder.getClient());
            existingOrder.setOrderDate(updatedOrder.getOrderDate());
            existingOrder.setOrderItems(updatedOrder.getOrderItems());
            orderRepository.save(existingOrder);
        }
    }

    public void deleteOrderById(Integer id) {
        orderRepository.deleteById(id);
    }
}
