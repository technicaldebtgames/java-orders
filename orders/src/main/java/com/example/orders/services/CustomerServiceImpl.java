package com.example.orders.services;

import com.example.orders.models.Customer;
import com.example.orders.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "customerService")
public class CustomerServiceImpl implements CustomerService
{

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public List<Customer> findAllCustomers()
    {
        List<Customer> customers = new ArrayList<>();
        customerRepository.findAll().iterator().forEachRemaining(customers::add);
        return customers;
    }

    @Override
    public Customer findCustomerById(long id)
    {
        return customerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Customer " + id + " not found."));
    }

    @Override
    public List<Customer> findCustomerByNameSubstring(String substr)
    {
        return customerRepository.findAllByCustnameContains(substr);
    }

    @Transactional
    @Override
    public Customer save(Customer customer)
    {
        return customerRepository.save(customer);
    }
}
