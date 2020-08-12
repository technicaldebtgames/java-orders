package com.example.orders.services;

import com.example.orders.models.Agent;
import com.example.orders.repositories.AgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "agentService")
public class AgentServiceImpl implements AgentService
{

    @Autowired
    AgentRepository agentRepository;

    @Override
    public Agent save(Agent agent)
    {
        return agentRepository.save(agent);
    }
}
