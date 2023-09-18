package com.example.damnbreadback.service;

import com.example.damnbreadback.dto.PostFilter;
import com.example.damnbreadback.entity.Post;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public interface HistoryService {

    List<Post> getHistory(Long userId) throws ExecutionException, InterruptedException;

}