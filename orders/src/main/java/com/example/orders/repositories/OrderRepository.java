package com.example.orders.repositories;

import com.example.orders.models.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long>
{
}
