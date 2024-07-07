package com.example.SpringVlogdata.Service;

import com.example.SpringVlogdata.Dto.PostDto;
import com.example.SpringVlogdata.Dto.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);
    List<PostDto> getAllpostData();
    PostDto getPostByID(Long id);
    PostDto updatePost(PostDto postDto, Long id);
    void deletePost(Long id);
    PostResponse getAllPages(int pageNo, int pageSize,String sortBy, String sortDir);
}
