package com.bionoor.api.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bionoor.api.models.Order;
import com.bionoor.api.models.Product;
import com.bionoor.api.models.Order.OrderStatus;
import com.bionoor.api.models.User;

public interface OrderRepository extends JpaRepository<Order, Long>{

 	public List<Order> findAllByStatus(OrderStatus status);
 	
 	 @Query("SELECT c FROM Order c WHERE YEAR(c.createdAt) = :annee AND c.fulfilled = :fulfilled")
 	public List<Order> findByFulfilledAndYears(boolean fulfilled, int annee);
 	 
 	 
 	@Query("SELECT c FROM Order c WHERE YEAR(c.createdAt) = :year ")
 	public List<Order> findByYear( int year);
 	public	Page<Order> findById(Long id , Pageable pageable);
 	public Page<Order> findByCustomerUsername(String username , Pageable pageable);
 	public Page<Order> findAllByStatus(OrderStatus status, Pageable pageable);
}
