package com.utcn.cmms.service.state;

import com.utcn.cmms.enums.TicketStatus;
import com.utcn.cmms.model.Ticket;
import org.springframework.stereotype.Component;
import com.utcn.cmms.exception.InvalidStateTransitionException;

@Component

public class PendingReviewState implements TicketState{
    @Override
    public TicketStatus getTicketStatus() {  return TicketStatus.PENDING_REVIEW; }

    @Override
    public void approve(Ticket ticket) {
        ticket.setStatus(TicketStatus.PAYMENT_PENDING);
    }

    @Override
    public void reject(Ticket ticket, String reason) {
        if(reason == null || reason.isEmpty())
            throw new InvalidStateTransitionException("Cannot reject a ticket without a reason.");
        ticket.setStatus(TicketStatus.IN_PROGRESS);
        ticket.setReasonOfRejection(reason);
    }

}
