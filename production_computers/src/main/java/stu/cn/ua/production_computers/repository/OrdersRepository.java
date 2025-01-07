package stu.cn.ua.production_computers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stu.cn.ua.production_computers.models.Orders;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Integer> {
}