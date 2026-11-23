package com.utcn.cmms.service.state;

import com.utcn.cmms.enums.TicketStatus;
import com.utcn.cmms.model.Ticket;
import org.springframework.stereotype.Component;

@Component

public class PaymentPendingState implements TicketState{
    @Override
    public TicketStatus getTicketStatus() {
        return TicketStatus.PAYMENT_PENDING;
    }

    @Override
    public void closed(Ticket ticket) {
        ticket.setStatus(TicketStatus.CLOSED);
    }
}
