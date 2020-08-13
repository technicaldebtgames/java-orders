package com.example.orders.controllers;

import com.example.orders.models.Agent;
import com.example.orders.services.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agents")
public class AgentController
{

    // make controllers small, only caring about calling the service methods and returning values

    @Autowired
    private AgentService agentService;

    // http://localhost:2019/agents
    @GetMapping(value = "/agents", produces = "application/json")
    public ResponseEntity<?> listAllAgents()
    {

        List<Agent> agents = agentService.findAllAgents();
        return new ResponseEntity<>(agents, HttpStatus.OK);

    }

    // http://localhost:2019/agents/agent/{id}
    @GetMapping(value = "/agent/{id}", produces = "application/json")
    public ResponseEntity<?> listAgentById(@PathVariable long id)
    {

        Agent agent = agentService.findAgentById(id);
        return new ResponseEntity<>(agent, HttpStatus.OK);

    }

}
