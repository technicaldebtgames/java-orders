package com.example.orders.controllers;

import com.example.orders.models.Order;
import com.example.orders.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/orders")
public class OrderController
{

    @Autowired
    private OrderService orderService;

    @GetMapping(value = "/order/{id}", produces = "application/json")
    public ResponseEntity<?> listOrderById(@PathVariable long id)
    {

        Order order = orderService.findOrderById(id);
        return new ResponseEntity<>(order, HttpStatus.OK);

    }

    @PostMapping(value = "/order", consumes = "application/json")
    public ResponseEntity<?> addNewOrder(@Valid @RequestBody Order order)
    {

        order.setOrdnum(0);
        order = orderService.save(order);

        HttpHeaders responseHeaders = new HttpHeaders();
        URI orderURI = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/" + order.getOrdnum()).build().toUri();
        responseHeaders.setLocation(orderURI);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);

    }

    @PutMapping(value = "/order/{id}", consumes = "application/json")
    public ResponseEntity<?> updateFullOrder(@Valid @RequestBody Order order, @PathVariable long id)
    {

        order.setOrdnum(id);
        orderService.save(order);

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @DeleteMapping(value = "/order/{id}")
    public ResponseEntity<?> deleteOrderById(@PathVariable long id)
    {

        orderService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);

    }

}
