package com.example.damnbreadback.service;

import com.example.damnbreadback.dto.PostDto;
import com.example.damnbreadback.entity.Scrap;
import com.example.damnbreadback.repository.ScrapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScrapServiceImpl implements ScrapService {

    @Autowired
    ScrapRepository scrapRepository;
    @Autowired
    PostService postService;

    @Override
    public List<PostDto> getScraps(Long userId) {
        List<Scrap> scraps = scrapRepository.getScrapsByUserUserId(userId);
        List<PostDto> scrapPosts = new ArrayList<>();
        scraps.forEach(s -> {
            PostDto postDto = PostDto.toDTO(s.getPost());
            scrapPosts.add(postDto);
        });
        return scrapPosts;

//        return scrapRepository.getScrapsByUserUserId(userId);
    }
}