package com.example.SpringVlogdata.Service.impl;

import com.example.SpringVlogdata.Dto.CommentDto;
import com.example.SpringVlogdata.Dto.PostDto;
import com.example.SpringVlogdata.Dto.PostResponse;
import com.example.SpringVlogdata.Entity.Comment;
import com.example.SpringVlogdata.Entity.Post;
import com.example.SpringVlogdata.Repository.CommentRepository;
import com.example.SpringVlogdata.Repository.PostRepository;
import com.example.SpringVlogdata.Service.PostService;
import com.example.SpringVlogdata.exception.ResourceNotExcep;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private PostRepository postRepository;

    private ModelMapper modelMapper;
    private final CommentRepository commentRepository;

    public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper,
                           CommentRepository commentRepository) {
        this.modelMapper = modelMapper;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        //convert dto to entity
        // ->extracted to method (mapTOEntity)
//        move to a method
//        Post post = new Post();
//        post.setTitle(postDto.getTitle());
//        post.setContent(postDto.getContent());
//        post.setDescription(postDto.getDescription());
        Post post =  mapToEntity(postDto);
        Post newpost= postRepository.save(post);

        //convert entity to Dto
        // ->extracted to method (mapTODto)
        //        move to a method
//        PostDto postResponse = new PostDto();
//        postResponse.setId(newpost.getId());
//        postResponse.setTitle(newpost.getTitle());
        PostDto postResponse = mapToDto(newpost);
        return postResponse;

    }
//1)convert dto to entity
    private Post mapToEntity(PostDto postDto){
        //using mapper
        Post post = modelMapper.map(postDto,Post.class);
//        Post post = new Post();
//        post.setTitle(postDto.getTitle());
//        post.setContent(postDto.getContent());
//        post.setDescription(postDto.getDescription());
//        post.setComments(converToEntity(postDto.getComments()));
//        return post;
        return post;
    }

//    private Set<Comment> converToEntity(Set<CommentDto> commentsDto) {
//        Set<Comment> comments = new HashSet<>();
//        for(CommentDto dto:commentsDto){
//            comments.add(converttoEntity(dto));
//        }
//    return comments;
//    }
//
//    private Comment converttoEntity(CommentDto dto) {
//        Comment comment = new Comment();
//        comment.setId(dto.getId());
//        // Map other fields from CommentDto to Comment
//        return comment;
//    }


    private  PostDto mapToDto(Post newpost) {
        //using mapper
//        PostDto postDto = modelMapper.map(newpost,PostDto.class);
        PostDto postResponse = new PostDto();
        postResponse.setId(newpost.getId());
        postResponse.setTitle(newpost.getTitle());
        postResponse.setContent(newpost.getContent());
        postResponse.setDescription(newpost.getDescription());
        return postResponse;
//        return postDto;
    }

    @Override
    public List<PostDto> getAllpostData() {
       List<Post> posts = postRepository.findAll();
       //convert list of posts entity into list of postDto i.e List<PostDto>
      return  posts.stream().map(post ->mapToDto(post)).collect(Collectors.toList());
    }

    @Override
    public PostDto getPostByID(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotExcep("post","id",id));
//       PostDto pst = new PostDto();
//       pst.setId(post.getId());
//       pst.setTitle(post.getTitle());
//       pst.setDescription(post.getDescription());
//       pst.setContent(post.getContent());
//        return pst;
        return mapToDto(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Long id) {
        //get post by id and update it
        Post post = postRepository.findById((id)).orElseThrow(() -> new ResourceNotExcep("post","id",id));
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        Post updatePost = postRepository.save(post);
        return mapToDto(updatePost);
    }

    @Override
    public void deletePost(Long id) {
        Post post = postRepository.findById((id)).orElseThrow(() -> new ResourceNotExcep("post","id",id));
         postRepository.delete(post);
    }

    @Override
    public PostResponse getAllPages(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Post> posts = postRepository.findAll(pageable);

        // get content for page object
        List<Post> listOfPosts = posts.getContent();

        List<PostDto> content= listOfPosts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalELements(posts.getTotalElements());
        postResponse.setTotalPage(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return postResponse;

    }


}
