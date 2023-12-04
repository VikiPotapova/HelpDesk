package com.potapova.helpdesk.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentOfUserDTO {
    private LocalDateTime date;
    private TicketForCommentDTO ticket;
    private String text;

}
