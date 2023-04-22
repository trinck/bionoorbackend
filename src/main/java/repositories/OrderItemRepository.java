package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import models.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{

}
