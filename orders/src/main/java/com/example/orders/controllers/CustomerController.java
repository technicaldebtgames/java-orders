package com.example.orders.controllers;

import com.example.orders.models.Customer;
import com.example.orders.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController
{

    @Autowired
    private CustomerService customerService;

    @GetMapping(value = "/orders", produces = "application/json")
    public ResponseEntity<?> listAllCustomers()
    {

        List<Customer> customers = customerService.findAllCustomers();
        return new ResponseEntity<>(customers, HttpStatus.OK);

    }

    @GetMapping(value = "/customer/{id}", produces = "application/json")
    public ResponseEntity<?> listCustomerById(@PathVariable long id)
    {

        Customer customer = customerService.findCustomerById(id);
        return new ResponseEntity<>(customer, HttpStatus.OK);

    }

    @GetMapping(value = "/namelike/{substr}", produces = "application/json")
    public ResponseEntity<?> listCustomersByNameSubstring(@PathVariable String substr)
    {

        List<Customer> customers = customerService.findCustomerByNameSubstring(substr);
        return new ResponseEntity<>(customers, HttpStatus.OK);

    }

}
