package com.utcn.cmms.service;

import com.utcn.cmms.enums.Priority;
import com.utcn.cmms.enums.TicketStatus;
import com.utcn.cmms.exception.ResourceNotFoundException;
import com.utcn.cmms.model.Ticket;
import com.utcn.cmms.repository.TicketRepository;
import com.utcn.cmms.service.state.TicketState;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final Map<TicketStatus, TicketState> stateMap;

    public TicketService(TicketRepository ticketRepository, List<TicketState> states){
        this.ticketRepository = ticketRepository;
        this.stateMap = states.stream().collect(Collectors.toMap(TicketState::getTicketStatus, Function.identity()));
    }

    @Transactional
    public Ticket createTicket(String title, String description, Priority priority, Integer clientId, Integer assetId){
        Ticket ticket = new Ticket();
        ticket.setTitle(title);
        ticket.setDescription(description);
        ticket.setPriority(priority);
        ticket.setClientId(clientId);
        ticket.setAssetId(assetId);
        ticket.setStatus(TicketStatus.REPORTED);
        return this.ticketRepository.save(ticket);
    }

    @Transactional
    public Ticket triageTicket(Integer ticketId) {
        Ticket ticket = getTicketOrThrow(ticketId);
        stateMap.get(ticket.getStatus()).triage(ticket);
        return ticket;
    }

    @Transactional
    public Ticket assignTicket(Integer ticketId, Integer technicianId){
        if(technicianId == null)
            throw new IllegalArgumentException("Technician id is required for assignment.");

        //TODO: wait for User and UserRepo to be implemented to be able to check if:
        //1) User with technicianId exists in the DB
        //2) User has 'Technician' role

        Ticket ticket = getTicketOrThrow(ticketId);
        stateMap.get(ticket.getStatus()).assign(ticket);
        ticket.setTechnicianId(technicianId);
        return ticket;
    }


    @Transactional
    public Ticket startWork(Integer ticketId){
        Ticket ticket = getTicketOrThrow(ticketId);
        stateMap.get(ticket.getStatus()).inProgress(ticket);
        return ticket;
    }

    @Transactional
    public Ticket submitReport(Integer ticketId, BigDecimal labourHours, String completionNotes){
        if(labourHours == null || labourHours.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Working hours must be greater than zero.");
        }
        Ticket ticket = getTicketOrThrow(ticketId);
        stateMap.get(ticket.getStatus()).pendingReview(ticket);
        ticket.setLaborHours(labourHours);
        ticket.setCompletionNotes(completionNotes);
        return ticket;
    }

    @Transactional
    public Ticket reviewTicket(Integer ticketId, boolean accepted, String reason){
        Ticket ticket = getTicketOrThrow(ticketId);
        TicketState state = stateMap.get(ticket.getStatus());
        if(accepted){
            state.approve(ticket);
            ticket.setReasonOfRejection(null); //if it was rejected -> in progress -> accepted, delete reason
        }else{
            state.reject(ticket, reason);
        }
        return ticket;
    }

    @Transactional
    public Ticket closeTicket(Integer ticketId){
        Ticket ticket = getTicketOrThrow(ticketId);
        stateMap.get(ticket.getStatus()).closed(ticket);
        return ticket;
    }

    public Ticket getTicketOrThrow(Integer id){
        if(id == null)
            throw new IllegalArgumentException("Ticket id cannot be null.");
        return ticketRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Ticket with id " + id + " was not found."));
    }

}
