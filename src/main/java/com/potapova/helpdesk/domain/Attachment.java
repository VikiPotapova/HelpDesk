package com.potapova.helpdesk.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
@Entity(name = "attachment")
public class Attachment {
    @Id
    @SequenceGenerator(name = "seq_attachment", sequenceName = "attachment_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_attachment")
    @Column(name = "id")
    private Long id;

    @Column(name = "url")
    private String url;

    @Column(name = "ticket_id")
    private Long ticketId;

    @Size(max = 20)
    @Column(name = "name")
    private String name;
}
