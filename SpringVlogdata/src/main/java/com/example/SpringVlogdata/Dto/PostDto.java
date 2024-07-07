package com.example.SpringVlogdata.Dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private Long id;
    //title should not be empty
    //atleast 2 char
    @NotEmpty
    @Size(min= 2,message = "post title should have atleast 2 chars")
    private String title;
    @NotEmpty
    private String description;
    @NotEmpty
    private String content;
    private Set<CommentDto> comments;
}
