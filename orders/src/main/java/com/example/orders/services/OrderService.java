package com.example.orders.services;

import com.example.orders.models.Order;

public interface OrderService
{

    Order findOrderById(long id);

    Order save(Order order);

}
