package com.example.SpringVlogdata.Controller;

import com.example.SpringVlogdata.Dto.PostDto;
import com.example.SpringVlogdata.Dto.PostResponse;
import com.example.SpringVlogdata.Service.PostService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    //with responseEntity
//    @PostMapping
//    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto){
//        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
//    }

    //without responseEntity
    @PostMapping
    public PostDto createPostResponseEntity(@Valid @RequestBody PostDto postDto){
        return postService.createPost(postDto);
    }

//    @GetMapping
//    public List<PostDto> getAllPost(){
//        return postService.getAllpostData();
//    }

    @GetMapping
    public PostResponse getAllPages(
            @RequestParam(value = "pageNo", defaultValue = "0",required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10",required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "id",required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ) {
        return postService.getAllPages(pageNo,pageSize, sortBy, sortDir);
    }

    //get postby id
    @GetMapping("/{id}")
    public PostDto getPostFromId(@PathVariable(name ="id") Long id){
        return postService.getPostByID(id);
    }
    //updateId
    @PutMapping("/{id}")
    public PostDto updatePost(@Valid @RequestBody PostDto postDto, @PathVariable Long id) {
        return postService.updatePost(postDto,id);
    }

    @DeleteMapping("/{id}")
    public String deleteAll(@PathVariable Long id) {
         postService.deletePost(id);
        return "Deleted Successfully " ;
    }
}
