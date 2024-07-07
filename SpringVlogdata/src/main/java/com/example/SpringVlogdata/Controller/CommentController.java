package com.example.SpringVlogdata.Controller;

import com.example.SpringVlogdata.Dto.CommentDto;
import com.example.SpringVlogdata.Service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable(value = "postId") Long postId,@RequestBody CommentDto commentDto){
        return new ResponseEntity<>(commentService.createComment(postId,commentDto), HttpStatus.CREATED);
    }

    @GetMapping("posts/{postId}/comments")
    public List<CommentDto> getCommentsByPostId(@PathVariable(value = "postId") Long postId){
        return commentService.getCommentsByPostId(postId);
    }

    @GetMapping("posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable(value = "postId") Long postId,
                                                     @PathVariable(value = "id") Long commentId){
            return new ResponseEntity<>(commentService.getCommentsById(postId, commentId),HttpStatus.OK);
    }
    @PutMapping("posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> upDateComment(@PathVariable(value = "postId")Long postId,
                                                    @PathVariable(value = "id") Long commentId,
                                                    @RequestBody CommentDto commentDto){
        return  new ResponseEntity<>(commentService.updateComment(postId,commentId,commentDto),HttpStatus.CREATED);
    }

    @DeleteMapping("posts/{postId}/comments/{id}")
    public String deleteComment(@PathVariable(value = "postId")Long postId,
                                @PathVariable(value = "id") Long commentId){
         commentService.deleteComment(postId,commentId);
         return "deletd";
    }
}
