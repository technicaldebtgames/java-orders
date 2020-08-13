package com.example.orders.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "agents")
public class Agent
{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private long agentcode;

    @Column(nullable = false)
    private String agentname;

    @Column(nullable = false)
    private String workingarea;

    @Column(nullable = false)
    private double commission;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String country;

    // must ignore properties that reference other entities to prevent recursion/infinite loops
    // note that these ignores ignore "themselves" in other entities/tables

    @OneToMany(mappedBy = "agent", cascade = CascadeType.ALL, orphanRemoval = true) // one agent (this class) to many customers
    @JsonIgnoreProperties("agent")
    private List<Customer> customers = new ArrayList<>();

    public Agent()
    {
    }

    public Agent(
        String agentname,
        String workingarea,
        double commission,
        String phone,
        String country)
    {
        this.agentname = agentname;
        this.workingarea = workingarea;
        this.commission = commission;
        this.phone = phone;
        this.country = country;
    }

    public long getAgentcode()
    {
        return agentcode;
    }

    public void setAgentcode(long agentcode)
    {
        this.agentcode = agentcode;
    }

    public String getAgentname()
    {
        return agentname;
    }

    public void setAgentname(String agentname)
    {
        this.agentname = agentname;
    }

    public String getWorkingarea()
    {
        return workingarea;
    }

    public void setWorkingarea(String workingarea)
    {
        this.workingarea = workingarea;
    }

    public double getCommission()
    {
        return commission;
    }

    public void setCommission(double commission)
    {
        this.commission = commission;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getCountry()
    {
        return country;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }

    public List<Customer> getCustomers()
    {
        return customers;
    }

    public void setCustomers(List<Customer> customers)
    {
        this.customers = customers;
    }

    @Override
    public String toString()
    {
        return "Agent{" +
            "agentcode=" + agentcode +
            ", agentname='" + agentname + '\'' +
            ", workingarea='" + workingarea + '\'' +
            ", commission=" + commission +
            ", phone='" + phone + '\'' +
            ", country='" + country + '\'' +
            '}';
    }
}
