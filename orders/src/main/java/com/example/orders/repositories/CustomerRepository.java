package com.example.orders.repositories;

import com.example.orders.models.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, Long>
{

    List<Customer> findAllByCustnameContains(String substr);

}
