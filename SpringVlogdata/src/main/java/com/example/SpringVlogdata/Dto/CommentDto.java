package com.example.SpringVlogdata.Dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class CommentDto {
    private long id;
    private String name;
    private String email;
    private String body;
}
