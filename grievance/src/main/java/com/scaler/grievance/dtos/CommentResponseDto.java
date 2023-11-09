package com.scaler.grievance.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentResponseDto {
    private Long commentId;
    private String commentMessage;
}
