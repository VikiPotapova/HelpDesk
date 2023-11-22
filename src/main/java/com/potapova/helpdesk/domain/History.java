package com.potapova.helpdesk.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@Entity
public class History {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private Ticket ticket;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime date;

    private String action;

    @ManyToOne
    private User user;

    @Column(nullable = false)
    @Size(max = 500)
    private String description;
}
