package com.utcn.cmms.service.state;
import com.utcn.cmms.enums.TicketStatus;
import com.utcn.cmms.model.Ticket;
import org.springframework.stereotype.Component;

@Component
public class ReportedState implements TicketState{

    @Override
    public TicketStatus getTicketStatus() {  return TicketStatus.REPORTED; }

    @Override
    public void triage(Ticket ticket){  ticket.setStatus(TicketStatus.TRIAGED); }

}
