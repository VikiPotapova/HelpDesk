package com.potapova.helpdesk.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
@Data
@Entity(name = "comment")
public class Comment {
    @Id
    @SequenceGenerator(name = "seq_comment", sequenceName = "comment_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_comment")
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Size(max = 500)
    @Column(name = "text")
    private String text;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date")
    private Timestamp date;

    @Column(name = "ticket_id")
    private Long ticketId;
}
