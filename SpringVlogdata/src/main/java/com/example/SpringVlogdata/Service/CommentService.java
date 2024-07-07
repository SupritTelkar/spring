package com.example.SpringVlogdata.Service;

import com.example.SpringVlogdata.Dto.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(Long postId, CommentDto commentDto);
    List<CommentDto> getCommentsByPostId(long postId);
    CommentDto getCommentsById(long postId, long commentId);
    CommentDto updateComment(long postId, long commentId,CommentDto commentDto);
    void deleteComment(long postId, long commentId);
}
