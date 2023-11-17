package com.potapova.helpdesk.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
@Data
@Entity(name = "ticket")
public class Ticket {
    @Id
    @SequenceGenerator(name = "seq_ticket", sequenceName = "ticket_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_ticket")
    @Column(name = "id")
    private Long id;

    @Size(max = 100)
    @Column(name = "name")
    private String name;

    @Size(max = 500)
    @Column(name = "description")
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_on")
    private Timestamp createdOn;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "desired_resolution_date")
    private Timestamp desiredResolutionDate;

    @Column(name = "assignee_id")
    private Long assigneeId;

    @Column(name = "owner_id")
    private Long ownerId;

    @Column(name = "state")
    private State state;

    @Column(name = "category")
    private Category category;

    @Column(name = "urgency")
    private Urgency urgency;

    @Column(name = "approved_id")
    private Long approvedId;

}
