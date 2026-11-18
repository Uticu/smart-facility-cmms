package com.utcn.cmms.model;

import com.utcn.cmms.enums.Priority;
import jakarta.persistence.*;

import com.utcn.cmms.enums.TicketStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private TicketStatus status = TicketStatus.REPORTED;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Priority priority;

    @Column(name = "client_id", nullable = false)
    private Integer clientId;

    @Column(name = "technician_id")
    private Integer technicianId;

    @Column(name = "asset_id", nullable = false)
    private Integer assetId;

    @Column(name = "sla_deadline")
    private LocalDateTime slaDeadline;

    @Column(name = "labor_hours", precision = 5, scale = 2)
    private BigDecimal laborHours;

    @Column(name = "completion_notes", columnDefinition = "TEXT")
    private String completionNotes;

    @Column(name = "reason_of_rejection", columnDefinition = "TEXT")
    private String reasonOfRejection;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public Ticket() {}
    public Integer getId() { return id; }
    public TicketStatus getStatus() { return status; }
    public void setStatus(TicketStatus status) { this.status = status; }
    public String getReasonOfRejection() { return reasonOfRejection; }
    public void setReasonOfRejection(String reasonOfRejection) { this.reasonOfRejection = reasonOfRejection; }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public void setTechnicianId(Integer technicianId) {
        this.technicianId = technicianId;
    }

    public void setAssetId(Integer assetId) {
        this.assetId = assetId;
    }

    public void setSlaDeadline(LocalDateTime slaDeadline) {
        this.slaDeadline = slaDeadline;
    }

    public void setLaborHours(BigDecimal laborHours) {
        this.laborHours = laborHours;
    }

    public void setCompletionNotes(String completionNotes) {
        this.completionNotes = completionNotes;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Priority getPriority() {
        return priority;
    }

    public Integer getClientId() {
        return clientId;
    }

    public Integer getTechnicianId() {
        return technicianId;
    }

    public Integer getAssetId() {
        return assetId;
    }

    public LocalDateTime getSlaDeadline() {
        return slaDeadline;
    }

    public BigDecimal getLaborHours() {
        return laborHours;
    }

    public String getCompletionNotes() {
        return completionNotes;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}