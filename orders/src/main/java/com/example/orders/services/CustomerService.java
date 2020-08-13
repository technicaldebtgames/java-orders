package com.example.orders.services;

import com.example.orders.models.Customer;

import java.util.List;

public interface CustomerService
{

    List<Customer> findAllCustomers();

    Customer findCustomerById(long id);

    List<Customer> findCustomerByNameSubstring(String substr);

    Customer save(Customer customer);

    void delete(long id);

    Customer update(Customer customer, long id);

}
