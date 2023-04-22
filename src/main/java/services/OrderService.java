package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import models.Order;
import models.User;

import repositories.OrderRepository;
import repositories.UserRepository;

@Service
public class OrderService {
    
    @Autowired
    private OrderRepository orderRepository;
    
   
    @Autowired
    private UserRepository userRepository;
    
   /* public Order createOrder(User user, Cart cart) {
        // create new order
        Order order = new Order();
       // order.setUser(user);
        //order.setCart(cart);
     //   order.setStatus(OrderStatus.PENDING);
     //   order.setCreatedAt(LocalDateTime.now());
        
        // save order and update cart
        orderRepository.save(order);
       // cart.setOrder(order);
        cartRepository.save(cart);
        
        return order;
    }*/
    
    public List<Order> getOrdersByUser(User user) {
        return orderRepository.findByUser(user);
    }
    
    public Order getOrderById(Long id) throws NotFoundException {
        return orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException());
    }
    
  /*  public Order updateOrderStatus(Long id, OrderStatus status) {
        Order order = getOrderById(id);
        order.setStatus(status);
        orderRepository.save(order);
        return order;
    }*/
    
    public void deleteOrder(Long id) throws NotFoundException {
        Order order = getOrderById(id);
        orderRepository.delete(order);
    }
}

