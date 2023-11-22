package com.potapova.helpdesk.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
public class Attachment {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String url;

    @ManyToOne
    private Ticket ticket;

    @Column(nullable = false)
    @Size(max = 20)
    private String name;
}
