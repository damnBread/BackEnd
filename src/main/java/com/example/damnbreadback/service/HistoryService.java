package com.example.damnbreadback.service;

import com.example.damnbreadback.dto.HistoryDto;
import com.example.damnbreadback.dto.PostDto;
import com.example.damnbreadback.dto.UserDTO;
import com.example.damnbreadback.entity.History;
import com.example.damnbreadback.entity.Post;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface HistoryService {

    List<PostDto> getHistory(Long userId) throws ExecutionException, InterruptedException;
    HistoryDto patchStatus(Long id, Long userid, int statusCode);
    List<UserDTO> getUserByHistory(Long damnId);

    Boolean isUnique(Long damnid, Long userid);
    Long createHistory(Long damnId, Long userId) throws ExecutionException, InterruptedException;
}