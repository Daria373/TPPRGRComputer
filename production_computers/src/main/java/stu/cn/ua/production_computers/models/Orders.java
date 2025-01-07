package stu.cn.ua.production_computers.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Clients client;

    @NotNull(message = "Order date is required")
    @PastOrPresent(message = "Order date cannot be in the future")
    private LocalDate orderDate;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItems> orderItems = new ArrayList<>();

    // Гетери та сетери
    public Integer getOrderId() {
        return orderId;
    }
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
    public Clients getClient() {
        return client;
    }
    public void setClient(Clients client) {
        this.client = client;
    }
    public LocalDate getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }
    public List<OrderItems> getOrderItems() {
        return orderItems;
    }
    public void setOrderItems(List<OrderItems> orderItems) {
        this.orderItems.clear();
        if (orderItems != null) {
            this.orderItems.addAll(orderItems);
        }
    }

    public void addOrderItem(OrderItems orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void removeOrderItem(OrderItems orderItem) {
        orderItems.remove(orderItem);
        orderItem.setOrder(null);
    }
}