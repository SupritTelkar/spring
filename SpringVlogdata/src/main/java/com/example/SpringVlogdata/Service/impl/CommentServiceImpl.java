package com.example.SpringVlogdata.Service.impl;

import com.example.SpringVlogdata.Dto.CommentDto;
import com.example.SpringVlogdata.Entity.Comment;
import com.example.SpringVlogdata.Entity.Post;
import com.example.SpringVlogdata.Repository.CommentRepository;
import com.example.SpringVlogdata.Repository.PostRepository;
import com.example.SpringVlogdata.Service.CommentService;
import com.example.SpringVlogdata.exception.BlogApiException;
import com.example.SpringVlogdata.exception.ResourceNotExcep;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private ModelMapper mapper;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper mapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    @Override
    public CommentDto createComment(Long postId, CommentDto commentDto) {
        Comment comment = mapToEntity(commentDto);
        //retrive post entity by id
        Post post = postRepository.findById(postId).orElseThrow(() ->new ResourceNotExcep("Post","id",postId));
        //set post to comment entity
        comment.setPost(post);
        //save comment entity to dataBase
        Comment newComment = commentRepository.save(comment);

        CommentDto dtoComment = mapToDto(newComment);
        return dtoComment;
    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {
        //retrive comments by postID
        List<Comment> comments = commentRepository.findByPostId(postId);
        return  comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentsById(long postId, long commentId) {
        //retrive post by its id
        Post post = postRepository.findById(postId).orElseThrow(() ->new ResourceNotExcep("post","id",postId));
        //retrive comment by comment id
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->new ResourceNotExcep("post","id",commentId));
        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"comment does nto belong to the post");
        }
        return mapToDto(comment);
    }

    @Override
    public CommentDto updateComment(long postId, long commentId, CommentDto commentDto) {
        Post post = postRepository.findById(postId).orElseThrow(() ->new ResourceNotExcep("post","id",postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->new ResourceNotExcep("post","id",commentId));
        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"comment does nto belong to the post");
        }
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
       Comment updatedComment = commentRepository.save(comment);
       return mapToDto(updatedComment);
    }

    @Override
    public void deleteComment(long postId, long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(() ->new ResourceNotExcep("post","id",postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->new ResourceNotExcep("post","id",commentId));
        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"comment does nto belong to the post");
        }
        commentRepository.delete(comment);
    }

    private CommentDto mapToDto (Comment comment){
        //using mapper
        CommentDto dto = mapper.map(comment,CommentDto.class);
//        CommentDto dto = new CommentDto();
//        dto.setId(comment.getId());
//        dto.setName(comment.getName());
//        dto.setBody(comment.getBody());
//        dto.setEmail(comment.getEmail());
//        return dto;
        return dto;
    }

    private Comment mapToEntity (CommentDto dto){
        //using mapper
        Comment comment = mapper.map(dto,Comment.class);
//        Comment comment = new Comment();
//        comment.setId(dto.getId());
//        comment.setName(dto.getName());
//        comment.setEmail(dto.getEmail());
//        comment.setBody(dto.getBody());
//        return  comment;
        return comment;
    }
}
