package com.example.orders.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order
{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private long ordnum;

    private double advanceamount;
    private double ordamount;
    private String orderdescription;

    @ManyToOne // there are many orders to one customer
    @JoinColumn(name = "custcode", nullable = false) // the primary key in customers to join on
    private Customer customer;

    @ManyToMany // join the payments table on the id of THIS orders table, id of which is called ordnum
    @JoinTable(name = "orderspayments",
        joinColumns = @JoinColumn(name = "ordnum"),
        inverseJoinColumns = @JoinColumn(name = "paymentid"))
    private Set<Payment> payments = new HashSet<>();

    public Order()
    {
    }

    public Order(
        double advanceamount,
        double ordamount,
        String orderdescription,
        Customer customer)
    {
        this.advanceamount = advanceamount;
        this.ordamount = ordamount;
        this.orderdescription = orderdescription;
        this.customer = customer;
    }

    public long getOrdnum()
    {
        return ordnum;
    }

    public void setOrdnum(long ordnum)
    {
        this.ordnum = ordnum;
    }

    public double getAdvanceamount()
    {
        return advanceamount;
    }

    public void setAdvanceamount(double advanceamount)
    {
        this.advanceamount = advanceamount;
    }

    public double getOrdamount()
    {
        return ordamount;
    }

    public void setOrdamount(double ordamount)
    {
        this.ordamount = ordamount;
    }

    public String getOrderdescription()
    {
        return orderdescription;
    }

    public void setOrderdescription(String orderdescription)
    {
        this.orderdescription = orderdescription;
    }

    public Customer getCustomer()
    {
        return customer;
    }

    public void setCustomer(Customer customer)
    {
        this.customer = customer;
    }

    public Set<Payment> getPayments()
    {
        return payments;
    }

    public void setPayments(Set<Payment> payments)
    {
        this.payments = payments;
    }

    @Override
    public String toString()
    {
        return "Order{" +
            "ordnum=" + ordnum +
            ", advanceamount=" + advanceamount +
            ", ordamount=" + ordamount +
            ", orderdescription='" + orderdescription + '\'' +
            ", customer=" + customer +
            '}';
    }
}
