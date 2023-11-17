package com.potapova.helpdesk.domain;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
@Data
@Entity(name = "feedback")
public class Feedback {
    @Id
    @SequenceGenerator(name = "seq_feedback", sequenceName = "feedback_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_feedback")
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "rate")
    private Integer rate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date")
    private Timestamp date;

    @Column(name = "text")
    private String text;

    @Column(name = "ticket_id")
    private Long ticketId;
}
