package com.potapova.helpdesk.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
@Data
@Entity(name = "history")
public class History {
    @Id
    @SequenceGenerator(name = "seq_history", sequenceName = "history_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_history")
    @Column(name = "id")
    private Long id;

    @Column(name = "ticket_id")
    private Long ticketId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date")
    private Timestamp date;

    @Column(name = "action")
    private String action;

    @Column(name = "user_id")
    private Long userId;

    @Size(max = 500)
    @Column(name = "description")
    private String description;
}
