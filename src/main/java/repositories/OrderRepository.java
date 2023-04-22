package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import models.Order;
import models.User;

public interface OrderRepository extends JpaRepository<Order, Long>{

	List<Order> findByUser(User user);

}
