package com.example.orders.services;

import com.example.orders.models.Customer;
import com.example.orders.models.Order;
import com.example.orders.repositories.CustomerRepository;
import com.example.orders.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Transactional
@Service(value = "orderService")
public class OrderServiceImpl implements OrderService
{

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public Order findOrderById(long id)
    {
        return orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Order " + id + " not found."));
    }

    @Transactional
    @Override
    public Order save(Order order)
    {

        Order formattedOrder = new Order();

        if (order.getOrdnum() != 0)
        {

            orderRepository.findById(order.getOrdnum()).orElseThrow(() -> new EntityNotFoundException("Order " + order.getOrdnum() + " not found."));

            formattedOrder.setOrdnum(order.getOrdnum());

        }

        formattedOrder.setOrdamount(order.getOrdamount());
        formattedOrder.setAdvanceamount(order.getAdvanceamount());
        formattedOrder.setOrderdescription(order.getOrderdescription());

        // many orders to one customer
        formattedOrder.setCustomer(null);

        Customer customer = customerRepository.findById(order.getCustomer().getCustcode()).orElseThrow(() -> new EntityNotFoundException("Customer " + order.getCustomer().getCustcode() + " not found."));

        formattedOrder.setCustomer(customer);

        return orderRepository.save(order);

    }

    @Transactional
    @Override
    public void delete(long id)
    {

        if (orderRepository.findById(id).isPresent())
        {

            orderRepository.deleteById(id);

        }
        else
        {

            throw new EntityNotFoundException("Order " + id + " not found.");

        }

    }
}
