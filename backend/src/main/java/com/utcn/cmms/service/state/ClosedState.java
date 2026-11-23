package com.utcn.cmms.service.state;

import com.utcn.cmms.enums.TicketStatus;
import org.springframework.stereotype.Component;


@Component
public class ClosedState implements TicketState{
    @Override
    public TicketStatus getTicketStatus() {
        return TicketStatus.CLOSED;
    }
}
