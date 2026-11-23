package com.utcn.cmms.repository;

import com.utcn.cmms.enums.TicketStatus;
import com.utcn.cmms.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {


    List<Ticket> findByStatus(TicketStatus status);
    List<Ticket> findByTechnicianId(Integer technicianId);
    List<Ticket> findByClientId(Integer clientId);
    List<Ticket> findByStatusNotAndSlaDeadlineBefore(TicketStatus status, LocalDateTime slaDeadline);

}
