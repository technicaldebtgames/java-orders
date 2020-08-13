package com.example.orders.controllers;

import com.example.orders.models.Customer;
import com.example.orders.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
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

    // this is likely the most complicated type of controller method you should make
    // if you are building one that is more complicated, you should probably reconsider
    // your general design and put the more complicated functionality elsewhere
    @PostMapping(value = "/customer", consumes = "application/json")
    public ResponseEntity<?> addNewCustomer(@Valid @RequestBody Customer customer)
    {

        customer.setCustcode(0); // the way spring data works, an id of zero is the same as null, meaning "create a new id"/"add a new object to system"
        customer = customerService.save(customer); // captures the new id/data generated during save

        HttpHeaders responseHeaders = new HttpHeaders();
        URI customerURI = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/" + customer.getCustcode()).build().toUri();
        responseHeaders.setLocation(customerURI);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);

    }

    // put will completely replace the record with whatever data is sent to it
    @PutMapping(value = "/customer/{id}", consumes = "application/json")
    public ResponseEntity<?> updateFullCustomer(@Valid @RequestBody Customer customer, @PathVariable long id)
    {

        customer.setCustcode(id); // set the custcode of the customer to be the id sent as a pathvariable
        customerService.save(customer);

        return new ResponseEntity<>(HttpStatus.OK); // they already know the location, so just return OK
        // the above is good from a security standpoint, to minimize the amount of talking between client and back end

    }

    @PatchMapping(value = "/customer/{id}", consumes = "application/json")
    public ResponseEntity<?> updateCustomer(@RequestBody Customer customer, @PathVariable long id)
    {

        customerService.update(customer, id);

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @DeleteMapping(value = "/customer/{id}")
    public ResponseEntity<?> deleteCustomerById(@PathVariable long id)
    {

        customerService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    /* //format for customer object
    {
        "custcode": 1,
        "custname": "Holmes",
        "custcity": "London",
        "workingarea": "London",
        "custcountry": "UK",
        "grade": "2",
        "openingamt": 6000.0,
        "receiveamt": 5000.0,
        "paymentamt": 7000.0,
        "outstandingamt": 4000.0,
        "phone": "BBBBBBB",
        "agent": {
            "agentcode": 3,
            "agentname": "Alford",
            "workingarea": "New York",
            "commission": 0.12,
            "phone": "044-25874365",
            "country": ""
        },
        "orders": []
    }
    */

}
