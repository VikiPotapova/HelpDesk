package com.potapova.helpdesk.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
public class Feedback {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private User user;

    private Integer rate;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime date;

    @Column(nullable = false)
    private String text;

    @ManyToOne
    private Ticket ticket;
}
