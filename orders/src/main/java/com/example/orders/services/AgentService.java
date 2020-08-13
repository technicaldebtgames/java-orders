package com.example.orders.services;

import com.example.orders.models.Agent;

import java.util.List;

public interface AgentService
{

    List<Agent> findAllAgents();

    Agent findAgentById(long id);

    Agent save(Agent agent);

}
