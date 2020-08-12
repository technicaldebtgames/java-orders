package com.example.orders.services;

import com.example.orders.models.Customer;
import com.example.orders.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "customerService")
public class CustomerServiceImpl implements CustomerService
{

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public Customer save(Customer customer)
    {
        return customerRepository.save(customer);
    }
}
