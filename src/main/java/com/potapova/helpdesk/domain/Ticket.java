package com.potapova.helpdesk.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
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
    @Temporal(TemporalType.DATE)
    private LocalDateTime createdOn;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate desiredResolutionDate;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private User assignee;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private User owner;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private User approved;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Urgency urgency;

}