package com.example.damnbreadback.dao;

import com.example.damnbreadback.entity.Chatroom;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Repository
@Slf4j
public class ChattingDao {

    public static final String COLLECTION_NAME = "chatting";

    public List<Chatroom> getChattings() throws ExecutionException, InterruptedException {
        return null;
    }
}
