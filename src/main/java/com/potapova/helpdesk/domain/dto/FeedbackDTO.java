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
public class FeedbackDTO {

    private LocalDateTime date;
    private Integer rate;
    private String text;
}