package com.example.damnbreadback.service;

import com.example.damnbreadback.entity.History;
import com.example.damnbreadback.entity.Post;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface HistoryService {

    List<Post> getHistory(Long userId) throws ExecutionException, InterruptedException;
    History patchStatus(Long id, Long userid, int statusCode);
}