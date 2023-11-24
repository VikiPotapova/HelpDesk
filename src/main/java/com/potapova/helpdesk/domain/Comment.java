package com.potapova.helpdesk.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private User user;

    @Column(nullable = false)
    @Size(max = 500)
    private String text;

    @Column(updatable = false, nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate date;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Ticket ticket;
}
