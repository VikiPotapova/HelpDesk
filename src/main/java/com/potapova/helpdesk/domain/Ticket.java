package com.potapova.helpdesk.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Size(max = 100)
    private String name;

    @Column(nullable = false)
    @Size(max = 500)
    private String description;

    @Column(updatable = false, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdOn;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime desiredResolutionDate;

    @ManyToOne(optional = false)
    private User assignee;

    @ManyToOne(optional = false)
    private User owner;

    @ManyToOne(optional = false)
    private User approved;

    @Column(nullable = false)
    private State state;

    @Column(nullable = false)
    private Category category;

    @Column(nullable = false)
    private Urgency urgency;

}
