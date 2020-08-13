package com.example.orders.services;

import com.example.orders.models.Agent;
import com.example.orders.models.Customer;
import com.example.orders.models.Order;
import com.example.orders.repositories.AgentRepository;
import com.example.orders.repositories.CustomerRepository;
import com.example.orders.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

// if any methods within the class are transaction, you should probably make the entire class transactional
@Transactional
@Service(value = "customerService")
public class CustomerServiceImpl implements CustomerService
{

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    AgentRepository agentRepository;

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

    // any time we are modifying data - saving, deleting, etc - we want it to be transactional
    // if one part of the process fails, the ENTIRE process should fail, to prevent data corruption/issues
    @Transactional
    @Override
    public Customer save(Customer customer)
    {

        Customer formattedCustomer = new Customer();

        // this was added for the put method, to validate the id sent
        if (customer.getCustcode() != 0)
        {

            customerRepository.findById(customer.getCustcode()).orElseThrow(() -> new EntityNotFoundException("Customer " + customer.getCustcode() + " not found."));

            formattedCustomer.setCustcode(customer.getCustcode());

        }

        formattedCustomer.setCustname(customer.getCustname());
        formattedCustomer.setCustcity(customer.getCustcity());
        formattedCustomer.setWorkingarea(customer.getWorkingarea());
        formattedCustomer.setCustcountry(customer.getCustcountry());
        formattedCustomer.setGrade(customer.getGrade());
        formattedCustomer.setOpeningamt(customer.getOpeningamt());
        formattedCustomer.setReceiveamt(customer.getReceiveamt());
        formattedCustomer.setPaymentamt(customer.getPaymentamt());
        formattedCustomer.setOutstandingamt(customer.getOutstandingamt());
        formattedCustomer.setPhone(customer.getPhone());

        // many to many relationship example - in this case it won't work, but this is the format for future relationships
        // add orders if given any from customer, and if not, generate error/validation issue
        /*formattedCustomer.getOrders().clear();

        for(Order o : customer.getOrders())
        {

            Order order = orderRepository.findById(o.getOrdnum()).orElseThrow(() -> new EntityNotFoundException("Order " + o.getOrdnum() + " not found."));

            // now that we have either thrown an error or found the order, we add it to the customer
            formattedCustomer.getOrders().add(order);

        }*/

        // one to many relationship
        formattedCustomer.getOrders().clear();

        for(Order o : customer.getOrders())
        {

            // this is one reason the jpa requires the default constructor
            Order order = new Order();

            order.setOrdamount(o.getOrdamount());
            order.setAdvanceamount(o.getAdvanceamount());
            order.setCustomer(formattedCustomer); // add the new customer to the new order
            order.setOrderdescription(o.getOrderdescription());

            // add the new order to the new customer
            formattedCustomer.getOrders().add(order);

        }

        // many to one relationship
        formattedCustomer.setAgent(null);

        Agent agent = agentRepository.findById(customer.getAgent().getAgentcode()).orElseThrow(() -> new EntityNotFoundException("Agent " + customer.getAgent().getAgentcode() + " not found."));

        formattedCustomer.setAgent(agent);

        // return and save the new customer
        return customerRepository.save(formattedCustomer);

    }

    @Transactional
    @Override
    public void delete(long id)
    {

        if (customerRepository.findById(id).isPresent())
        {

            customerRepository.deleteById(id);

        }
        else
        {

            throw new EntityNotFoundException("Customer " + id + " not found.");

        } // this was just a different way of doing a response to an invalid id; the other way is above in the save method

    }

    @Transactional
    @Override
    public Customer update(
        Customer customer,
        long id)
    {

        // if a customer is found, cache it in a new customer. if not, throw.
        Customer formattedCustomer = customerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Customer " + id + " not found."));

        if (customer.getCustname() != null)
            formattedCustomer.setCustname(customer.getCustname());

        if (customer.getCustcity() != null)
            formattedCustomer.setCustcity(customer.getCustcity());

        if (customer.getWorkingarea() != null)
            formattedCustomer.setWorkingarea(customer.getWorkingarea());

        if (customer.getCustcountry() != null)
            formattedCustomer.setCustcountry(customer.getCustcountry());

        if (customer.hasvalueforgrade) // primitives need handled differently in the model to account for null issue
            formattedCustomer.setGrade(customer.getGrade());

        if (customer.hasvalueforopeningamt)
            formattedCustomer.setOpeningamt(customer.getOpeningamt());

        if (customer.hasvalueforreceiveamt)
            formattedCustomer.setReceiveamt(customer.getReceiveamt());

        if (customer.hasvalueforpaymentamt)
            formattedCustomer.setPaymentamt(customer.getPaymentamt());

        if (customer.hasvalueforoutstandingamt)
            formattedCustomer.setOutstandingamt(customer.getOutstandingamt());

        if (customer.getPhone() != null)
            formattedCustomer.setPhone(customer.getPhone());

        // one to many relationship
        if (customer.getOrders().size() > 0)
        {

            formattedCustomer.getOrders().clear();

            for(Order o : customer.getOrders())
            {

                // this is one reason the jpa requires the default constructor
                Order order = new Order();

                order.setOrdamount(o.getOrdamount());
                order.setAdvanceamount(o.getAdvanceamount());
                order.setCustomer(formattedCustomer); // add the new customer to the new order
                order.setOrderdescription(o.getOrderdescription());

                // add the new order to the new customer
                formattedCustomer.getOrders().add(order);

            }

        }

        if (customer.getAgent() != null)
        {

            // many to one relationship
            formattedCustomer.setAgent(null);

            Agent agent = agentRepository.findById(customer.getAgent().getAgentcode()).orElseThrow(() -> new EntityNotFoundException("Agent " + customer.getAgent().getAgentcode() + " not found."));

            formattedCustomer.setAgent(agent);

        }

        // return and save the new customer
        return customerRepository.save(formattedCustomer);

    }

}
