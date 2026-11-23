package com.utcn.cmms.service.state;

import com.utcn.cmms.enums.TicketStatus;
import com.utcn.cmms.model.Ticket;
import org.springframework.stereotype.Component;

@Component

public class AssignedState implements TicketState{
    @Override
    public TicketStatus getTicketStatus() {  return TicketStatus.ASSIGNED; }

    @Override
    public void inProgress(Ticket ticket){  ticket.setStatus(TicketStatus.IN_PROGRESS); }
}
