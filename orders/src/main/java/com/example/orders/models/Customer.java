package com.example.orders.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customers")
public class Customer
{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private long custcode;

    @Column(nullable = false)
    private String custname;

    @Column(nullable = false)
    private String custcity;

    @Column(nullable = false)
    private String workingarea;

    @Column(nullable = false)
    private String custcountry;

    @Column(nullable = false)
    private char grade;

    @Column(nullable = false)
    private double openingamt;

    @Column(nullable = false)
    private double receiveamt;

    @Column(nullable = false)
    private double paymentamt;

    @Column(nullable = false)
    private double outstandingamt;

    @Column(nullable = false)
    private String phone;

    // there are many customers (this class) to one agent
    @ManyToOne
    @JoinColumn(name = "agentcode") // this is the primary key in agents, may generate hibernate error if wrong
    @JsonIgnoreProperties("customers")
    private Agent agent;

    // mappedBy is a field inside of Order to match, which is the ManyToOne variable customer
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true) // one cust (this) to many orders
    @JsonIgnoreProperties("customer")
    private List<Order> orders = new ArrayList<>();

    public Customer()
    {
    }

    public Customer(
        String custname,
        String custcity,
        String workingarea,
        String custcountry,
        char grade,
        double openingamt,
        double receiveamt,
        double paymentamt,
        double outstandingamt,
        String phone,
        Agent agent)
    {
        this.custname = custname;
        this.custcity = custcity;
        this.workingarea = workingarea;
        this.custcountry = custcountry;
        this.grade = grade;
        this.openingamt = openingamt;
        this.receiveamt = receiveamt;
        this.paymentamt = paymentamt;
        this.outstandingamt = outstandingamt;
        this.phone = phone;
        this.agent = agent;
    }

    public long getCustcode()
    {
        return custcode;
    }

    public void setCustcode(long custcode)
    {
        this.custcode = custcode;
    }

    public String getCustname()
    {
        return custname;
    }

    public void setCustname(String custname)
    {
        this.custname = custname;
    }

    public String getCustcity()
    {
        return custcity;
    }

    public void setCustcity(String custcity)
    {
        this.custcity = custcity;
    }

    public String getWorkingarea()
    {
        return workingarea;
    }

    public void setWorkingarea(String workingarea)
    {
        this.workingarea = workingarea;
    }

    public String getCustcountry()
    {
        return custcountry;
    }

    public void setCustcountry(String custcountry)
    {
        this.custcountry = custcountry;
    }

    public char getGrade()
    {
        return grade;
    }

    public void setGrade(char grade)
    {
        this.grade = grade;
    }

    public double getOpeningamt()
    {
        return openingamt;
    }

    public void setOpeningamt(double openingamt)
    {
        this.openingamt = openingamt;
    }

    public double getReceiveamt()
    {
        return receiveamt;
    }

    public void setReceiveamt(double receiveamt)
    {
        this.receiveamt = receiveamt;
    }

    public double getPaymentamt()
    {
        return paymentamt;
    }

    public void setPaymentamt(double paymentamt)
    {
        this.paymentamt = paymentamt;
    }

    public double getOutstandingamt()
    {
        return outstandingamt;
    }

    public void setOutstandingamt(double outstandingamt)
    {
        this.outstandingamt = outstandingamt;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public Agent getAgent()
    {
        return agent;
    }

    public void setAgent(Agent agent)
    {
        this.agent = agent;
    }

    public List<Order> getOrders()
    {
        return orders;
    }

    public void setOrders(List<Order> orders)
    {
        this.orders = orders;
    }

    @Override
    public String toString()
    {
        return "Customer{" +
            "custcode=" + custcode +
            ", custname='" + custname + '\'' +
            ", custcity='" + custcity + '\'' +
            ", workingarea='" + workingarea + '\'' +
            ", custcountry='" + custcountry + '\'' +
            ", grade=" + grade +
            ", openingamt=" + openingamt +
            ", receiveamt=" + receiveamt +
            ", paymentamt=" + paymentamt +
            ", outstandingamt=" + outstandingamt +
            ", phone='" + phone + '\'' +
            ", agent=" + agent.toString() +
            '}';
    }
}
