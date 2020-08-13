package com.example.orders.services;

import com.example.orders.models.Agent;
import com.example.orders.repositories.AgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // note a different package than default javax

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "agentService")
public class AgentServiceImpl implements AgentService
{

    @Autowired
    AgentRepository agentRepository;

    // implement logic here for what gets returned to controllers by calling repo methods, sorting, formatting, etc

    @Override
    public List<Agent> findAllAgents()
    {
        List<Agent> agents = new ArrayList<>();
        agentRepository.findAll().iterator().forEachRemaining(agents::add);
        return agents;
    }

    @Override
    public Agent findAgentById(long id)
    {
        // if this request doesn't find the agent (because it is returning an optional)
        // instead throw error
        return agentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Agent " + id + " not found."));
    }

    @Transactional
    @Override
    public Agent save(Agent agent)
    {
        return agentRepository.save(agent);
    }
}
