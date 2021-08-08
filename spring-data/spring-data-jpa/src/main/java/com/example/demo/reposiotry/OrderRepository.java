package com.example.demo.reposiotry;

import com.example.demo.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, String>, OrderRepositoryCustom {

}
