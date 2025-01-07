package stu.cn.ua.production_computers.service;

import stu.cn.ua.production_computers.models.OrderItems;
import stu.cn.ua.production_computers.repository.OrderItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderItemService {

    @Autowired
    private OrderItemsRepository orderItemRepository;

    public List<OrderItems> getAllOrderItems() {
        return orderItemRepository.findAll();
    }

    public Optional<OrderItems> findOrderItemById(Integer id) {
        return orderItemRepository.findById(id);
    }

    public void saveOrderItem(OrderItems orderItem) {
        orderItemRepository.save(orderItem);
    }

    public void updateOrderItem(OrderItems updatedOrderItem) {
        Optional<OrderItems> existingOrderItemOpt = orderItemRepository.findById(updatedOrderItem.getOrderItemId());

        if (existingOrderItemOpt.isPresent()) {
            OrderItems existingOrderItem = existingOrderItemOpt.get();
            existingOrderItem.setOrder(updatedOrderItem.getOrder());
            existingOrderItem.setComponent(updatedOrderItem.getComponent());
            existingOrderItem.setQuantity(updatedOrderItem.getQuantity());
            orderItemRepository.save(existingOrderItem);
        }
    }

    public void deleteOrderItemById(Integer id) {
        orderItemRepository.deleteById(id);
    }
}
