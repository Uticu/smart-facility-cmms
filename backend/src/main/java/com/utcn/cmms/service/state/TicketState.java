package com.utcn.cmms.service.state;

import com.utcn.cmms.enums.TicketStatus;
import com.utcn.cmms.exception.InvalidStateTransitionException;
import com.utcn.cmms.model.Ticket;


/**
*   Interface used to switch/approve/reject different states
*   <p>
*   Each class that implements this interface will only {@code @Override} the method
*   for the next state, with the sole exception of state {@code PENDING_REVIEW}, which
*   also overrides {@code approve()} and {@code reject()}
*
**/
public interface TicketState {

    public TicketStatus getTicketStatus();
    default void approve(Ticket ticket) {
        throw new InvalidStateTransitionException("Cannot approve from state: " + ticket.getStatus());
    }
    default void reject(Ticket ticket, String reason) {
        throw new InvalidStateTransitionException("Cannot reject from state: " + ticket.getStatus());
    }
    default void triage(Ticket ticket){
        throw new InvalidStateTransitionException("Cannot switch to TRIAGE state from state: " + ticket.getStatus());
    }
    default void assign(Ticket ticket){
        throw new InvalidStateTransitionException("Cannot switch to ASSIGNED state from state: " + ticket.getStatus());
    }
    default void inProgress(Ticket ticket){
        throw new InvalidStateTransitionException("Cannot switch to IN_PROGRESS state from state: " + ticket.getStatus());
    }
    default void pendingReview(Ticket ticket){
        throw new InvalidStateTransitionException("Cannot switch to PENDING_REVIEW state from state: " + ticket.getStatus());
    }
    default void closed(Ticket ticket){
        throw new InvalidStateTransitionException("Cannot switch to CLOSED state from state: " + ticket.getStatus());

    }
}
