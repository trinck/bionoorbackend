package com.bionoor.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bionoor.api.models.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{

}
