package com.example.damnbreadback.service;

import com.example.damnbreadback.dto.PostDto;

import java.util.List;

public interface ScrapService {
    List<PostDto> getScraps(Long userId);
}