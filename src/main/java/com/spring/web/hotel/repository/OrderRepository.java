package com.spring.web.hotel.repository;

import com.spring.web.hotel.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
