package com.potapova.helpdesk.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Ticket ticket;

    @Column(updatable = false, nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate date;

    @Column(nullable = false)
    private String action;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private User user;

    @Column(nullable = false)
    @Size(max = 500)
    private String description;
}
