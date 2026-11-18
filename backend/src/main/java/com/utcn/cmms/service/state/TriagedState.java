package com.utcn.cmms.service.state;
import com.utcn.cmms.enums.TicketStatus;
import com.utcn.cmms.model.Ticket;
import org.springframework.stereotype.Component;

@Component
public class TriagedState implements TicketState {
    @Override
    public TicketStatus getTicketStatus() {  return TicketStatus.TRIAGED; }

    @Override
    public void assign(Ticket ticket){  ticket.setStatus(TicketStatus.ASSIGNED); }
}
