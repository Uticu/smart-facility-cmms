package com.utcn.cmms.service.state;

import com.utcn.cmms.enums.TicketStatus;
import com.utcn.cmms.model.Ticket;
import org.springframework.stereotype.Component;

@Component

public class InProgressState implements TicketState{

    @Override
    public TicketStatus getTicketStatus() {
        return TicketStatus.IN_PROGRESS;
    }

    @Override
    public void pendingReview(Ticket ticket){
        ticket.setStatus(TicketStatus.PENDING_REVIEW);
    }
}
