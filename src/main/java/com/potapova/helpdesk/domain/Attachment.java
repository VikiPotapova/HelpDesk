package com.potapova.helpdesk.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String url;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Ticket ticket;

    @Column(nullable = false)
    @Size(max = 20)
    private String name;
}