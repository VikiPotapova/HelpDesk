package com.potapova.helpdesk.domain.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeedbackDTO {

    @CreationTimestamp
    private LocalDateTime date;
    @NotNull
    @Min(1)
    @Max(5)
    private Integer rate;
    @NotBlank
    private String text;
}